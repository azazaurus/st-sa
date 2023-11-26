package ru.itislabs.tests.hash;

import ru.itislabs.hash.*;

import java.security.*;

public final class DefaultHashServiceFactory {
	private DefaultHashServiceFactory() {
	}

	public static final String cryptographicHashFunctionName = "SHA-256";

	public static DefaultHashService create() {
		return new DefaultHashService(
			DefaultHashServiceFactory::initializeCryptographicHashFunction);
	}

	private static MessageDigest initializeCryptographicHashFunction() {
		try {
			return MessageDigest.getInstance(cryptographicHashFunctionName);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
