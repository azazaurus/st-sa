package ru.itislabs.tests.signatures;

import ru.itislabs.signatures.*;

import java.security.*;
import java.security.spec.*;
import java.util.*;

public final class DefaultSignatureServiceFactory {
	private DefaultSignatureServiceFactory() {
	}

	public static final String keyAlgorithm = "RSA";
	public static final String signatureAlgorithm = "SHA256withRSA";

	private static final Base64.Decoder base64Decoder = Base64.getDecoder();
	private static final String x509PublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4coCDH0mXz+vPXF5fuA0CZDyPcXjAVSzidezsaJtZWe6ZGSLRNYc7B914WxKWhHgUNwgbGBp8cRb4vjfSwyRlgzDGKK4HaJGYCTj2FVlEFvSbt4iv3W4xWpusuKQYz/ChV4sbj9A8dHuW/LujxjuHNOBsmaeanrjJ646p9Z8BJwIDAQAB";
	private static final String pkcs8PrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALhygIMfSZfP689cXl+4DQJkPI9xeMBVLOJ17Oxom1lZ7pkZItE1hzsH3XhbEpaEeBQ3CBsYGnxxFvi+N9LDJGWDMMYorgdokZgJOPYVWUQW9Ju3iK/dbjFam6y4pBjP8KFXixuP0Dx0e5b8u6PGO4c04GyZp5qeuMnrjqn1nwEnAgMBAAECgYAvMdUiN+I7vw7mawsW0OA7SWyZg35TMDgg5e2ufAGr9i/v6peXEmw8tYF8p0j1TIXxHjervxof10v6b6mW+ymXhC4Jg3mPpkMmTIE+5OLJ6CLMu/yes7+n9gVLSTu0fDZNzNzKFLS3JKXJMJEiJxd2MmVePybyc8m11kunusmgcQJBAO6naOeoo0GeTQI0gJPgObxvaUbAAW3Ksqxk1LwL1cKcUHa+MwYV1S0w3uvWkVUmiEAqh+xvDxR/LY12xKbT8IkCQQDF2nqG4NtuH/A2s29SJvjf95q6SRsgUy73C7JRF/vZ+hOsUj6ppyuU89Nu2o7QA3dBmRtGsI8pftlbL2uwoRgvAkEAlB9LuRyt1zhNS6USeLWeoBn2GXdqa553LxCWCPC/h75FIq4CNTCv0xCWC4JtSZBU2J6NJm33yhz1ROwGOhUpIQJANGZpnwE8fE0PJJ2fNQXHUiHUo9Oq0IPXY2yAnIOamx9lSHU7zVjN7RQqgih2PFc+pWv0UmN+dwH2dPT5M31m1wJAHFBBJ79wUfuhURPb/JkOMQtWRC2Er+6xswFRH8azjfmBXJv32NU1+BW3poasvBCTO+6cE2Zof3MfOUyAF3KVYA==";

	private static final KeyFactory keyFactory = createKeyFactory();
	private static final PublicKey publicKey = getPublicKey();
	private static final PrivateKey privateKey = getPrivateKey();

	public static DefaultSignatureService create() {
		return new DefaultSignatureService(
			DefaultSignatureServiceFactory::getSignature,
			() -> publicKey,
			() -> privateKey);
	}

	private static Signature getSignature() {
		try {
			return Signature.getInstance(signatureAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static KeyFactory createKeyFactory() {
		try {
			return KeyFactory.getInstance(keyAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static PublicKey getPublicKey() {
		try {
			var decodedPublicKey = base64Decoder.decode(x509PublicKey);
			return keyFactory.generatePublic(new X509EncodedKeySpec(decodedPublicKey));
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	private static PrivateKey getPrivateKey() {
		try {
			var decodedPrivateKey = base64Decoder.decode(pkcs8PrivateKey);
			return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedPrivateKey));
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
}
