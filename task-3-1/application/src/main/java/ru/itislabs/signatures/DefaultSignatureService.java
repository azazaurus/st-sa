package ru.itislabs.signatures;

import java.security.*;
import java.security.spec.*;
import java.util.function.*;

public class DefaultSignatureService implements SignatureService {
	private final Supplier<Signature> signatureProvider;
	private final PublicKey publicKey;
	private final PrivateKey privateKey;

	public DefaultSignatureService(
			Supplier<Signature> signatureProvider,
			Supplier<PublicKey> publicKeyProvider,
			Supplier<PrivateKey> privateKeyProvider) {
		this.signatureProvider = signatureProvider;

		publicKey = publicKeyProvider.get();
		privateKey = privateKeyProvider.get();
	}

	@Override
	public boolean verify(byte[] dataToVerify, byte[] signatureBody) {
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
}
