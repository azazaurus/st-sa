package ru.itislabs.signatures;

import java.security.*;
import java.util.function.*;

public class DefaultSignatureService implements SignatureService {
	private final Supplier<Signature> signatureProvider;
	private final PublicKey publicKey;
	private final PrivateKey privateKey;
	private final PublicKey arbiterPublicKey;

	public DefaultSignatureService(
			Supplier<Signature> signatureProvider,
			Supplier<PublicKey> publicKeyProvider,
			Supplier<PrivateKey> privateKeyProvider,
			Supplier<PublicKey> arbiterPublicKeyProvider) {
		this.signatureProvider = signatureProvider;

		publicKey = publicKeyProvider.get();
		privateKey = privateKeyProvider.get();
		arbiterPublicKey = arbiterPublicKeyProvider.get();
	}

	@Override
	public boolean verify(byte[] dataToVerify, byte[] signatureBody) {
		return verify(dataToVerify, signatureBody, publicKey);
	}

	@Override
	public boolean verifyWithArbiter(byte[] dataToVerify, byte[] signatureBody) {
		return verify(dataToVerify, signatureBody, arbiterPublicKey);
	}

	@Override
	public byte[] sign(byte[] dataToSign) {
		try {
			var signature = signatureProvider.get();
			signature.initSign(privateKey);
			signature.update(dataToSign);
			return signature.sign();
		} catch (InvalidKeyException | SignatureException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean verify(byte[] dataToVerify, byte[] signatureBody, PublicKey publicKey) {
		try {
			var signature = signatureProvider.get();
			signature.initVerify(publicKey);
			signature.update(dataToVerify);
			return signature.verify(signatureBody);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (SignatureException e) {
			return false;
		}
	}
}
