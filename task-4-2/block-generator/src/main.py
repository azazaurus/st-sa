import hashlib
import json
import struct
import urllib

import numpy as np
import pandas as pd
import requests
from cryptography.hazmat.backends import default_backend as crypto_default_backend
from cryptography.hazmat.primitives import hashes, serialization
from cryptography.hazmat.primitives.asymmetric import padding, rsa
from sklearn.model_selection import train_test_split
from sklearn.neural_network import MLPRegressor
from sklearn.preprocessing import MinMaxScaler

public_key_filename = "public.key"
private_key_filename = "private.key"
training_data_filename = "test_data_100.csv"

blockchain_url = "http://itislabs.ru/nbc/chain"
new_block_url = "http://itislabs.ru/nbc/newblock"


def get_key_pair(public_key_filename, private_key_filename):
	new_key = True
	try:
		with open(private_key_filename, "rb") as private_key_file:
			encoded_private_key = private_key_file.read()
			private_key = serialization.load_der_private_key(encoded_private_key, password = None)
		new_key = False
	except:
		private_key = generate_key_pair()

	public_key = private_key.public_key()

	if new_key:
		with open(public_key_filename, "wb") as public_key_file:
			encoded_public_key = public_key.public_bytes(
				serialization.Encoding.DER,
				serialization.PublicFormat.SubjectPublicKeyInfo)
			public_key_file.write(encoded_public_key)
		with open(private_key_filename, "wb") as private_key_file:
			encoded_private_key = private_key.private_bytes(
				serialization.Encoding.DER,
				serialization.PrivateFormat.PKCS8,
				serialization.NoEncryption())
			private_key_file.write(encoded_private_key)
		print("Generated new private key")

	return public_key, private_key


def generate_key_pair():
	return rsa.generate_private_key(65537, 1024, backend = crypto_default_backend())


def train_model(df):
	df = df.dropna()

	X = df[df.columns[:-1]]
	y = df[df.columns[-1]]

	X = MinMaxScaler().fit_transform(X)
	y = MinMaxScaler().fit_transform(y.values.reshape(-1, 1))

	X_train, X_test, y_train, y_test = train_test_split(X, y, test_size = 0.2, random_state = 0)

	model = MLPRegressor(hidden_layer_sizes = (2, 3), max_iter = 50000, activation = 'logistic')
	model.fit(X_train, y_train.ravel())
	error = np.square(np.subtract(y_test, model.predict(X_test))).mean()

	print('Model error:', error)

	return model.coefs_, error


def get_previous_block():
	req = requests.get(blockchain_url)
	response = req.json()
	return response[-1]


def calculate_block_hash(block):
	prevhash_bytes = bytes.fromhex(block['prevhash'])
	data_bytes = to_json(block['data']).encode()
	signature_bytes = bytes.fromhex(block['signature'])
	nonce_bytes = struct.pack(">I", block['nonce'])

	data_to_hash = prevhash_bytes + data_bytes + signature_bytes + nonce_bytes

	hasher = hashlib.sha256()
	hasher.update(data_to_hash)
	return hasher.hexdigest()


def sign_string(string, private_key):
	return sign_bytes(string.encode(), private_key)


def sign_bytes(bytes_, private_key):
	return private_key.sign(
		bytes_,
		padding.PSS(
			mgf = padding.MGF1(hashes.SHA256()),
			salt_length = padding.PSS.MAX_LENGTH),
		hashes.SHA256())


def create_block(weights, error, public_key, private_key, previous_block_hash):
	block_data = {
		"w11": to_str_rounded(weights[0][0][0]),
		"w12": to_str_rounded(weights[0][0][1]),
		"w21": to_str_rounded(weights[0][1][0]),
		"w22": to_str_rounded(weights[0][1][1]),
		"v11": to_str_rounded(weights[1][0][0]),
		"v12": to_str_rounded(weights[1][0][1]),
		"v13": to_str_rounded(weights[1][0][2]),
		"v21": to_str_rounded(weights[1][1][0]),
		"v22": to_str_rounded(weights[1][1][1]),
		"v23": to_str_rounded(weights[1][1][2]),
		"w1": to_str_rounded(weights[2][0][0]),
		"w2": to_str_rounded(weights[2][1][0]),
		"w3": to_str_rounded(weights[2][2][0]),
		"e": to_str_rounded(error),
		"publickey": bytes.hex(
			public_key.public_bytes(
				serialization.Encoding.PEM,
				serialization.PublicFormat.SubjectPublicKeyInfo))
	}
	block_data_str = to_json(block_data)
	block_signature = sign_string(block_data_str, private_key)
	block = {
		"prevhash": previous_block_hash,
		"data": block_data,
		"signature": bytes.hex(block_signature)
	}

	difficulty = 3
	prefix_str = "0" * difficulty
	nonce = 0
	while True:
		if nonce > 0 and nonce % 10000 == 0:
			print(f"Trying nonce = {nonce}")

		block["nonce"] = nonce
		block_hash = calculate_block_hash(block)

		if block_hash.startswith(prefix_str):
			print(f"Successfully brute forced nonce = {nonce}")
			break
		nonce += 1

	return block, block_hash


def send_new_block(block):
	encoded_block = urllib.parse.quote(to_json(block), safe = '')
	response = requests.get(new_block_url + "?block=" + encoded_block)
	if not response.ok:
		print(f"Status code {response.status_code}: {response.text}")
		return False

	result = json.loads(response.text)
	if result["status"] != 0:
		print(f"Error: {response.text}")
		return False

	return True


def to_str_rounded(num):
	return "{:.12f}".format(num).rstrip('0').rstrip('.')


def to_json(data):
	return json.dumps(data, indent = None, separators = (',', ':'))


def main():
	public_key, private_key = get_key_pair(public_key_filename, private_key_filename)

	df = pd.read_csv(training_data_filename, delimiter = ';', header = None)
	weights, error = train_model(df)

	previous_block = get_previous_block()
	previous_block_hash = calculate_block_hash(previous_block)
	new_block, new_block_hash = create_block(weights, error, public_key, private_key, previous_block_hash)
	new_block_added = send_new_block(new_block)
	if not new_block_added:
		return

	print(f"Block with hash {new_block_hash} has been added")


if __name__ == '__main__':
	main()
