package ru.itislabs.configuration;

import ru.itislabs.blockchains.*;
import ru.itislabs.hash.*;
import ru.itislabs.signatures.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;

public final class Configurator {
	private static final String configurationFileName = "application.properties";

	private Configurator() {
	}

	public static Properties readConfiguration(ClassLoader classLoader) throws IOException {
		try (var configurationStream = classLoader.getResourceAsStream(configurationFileName)) {
			var configuration = new Properties();
			configuration.load(configurationStream);
			return configuration;
		}
	}

	public static BlockchainService initializeBlockchainService(Properties configuration) {
		var blockchainFileName = configuration.getProperty("blockchain.filename");

		var hashService = new DefaultHashService(Configurator::initializeCryptographicHashFunction);
		var signatureService = initializeDefaultSignatureService(configuration);
		return new BlockchainService(
			new BlockchainRepository(
				() -> initializeBlockchainInputStream(blockchainFileName),
				() -> initializeBlockchainOutputStream(blockchainFileName)),
			new BlockDraftFactory(configuration, hashService, signatureService),
			hashService,
			signatureService);
	}

	private static MessageDigest initializeCryptographicHashFunction() {
		var cryptographicHashFunctionName = "SHA-256";
		try {
			return MessageDigest.getInstance(cryptographicHashFunctionName);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static DefaultSignatureService initializeDefaultSignatureService(Properties configuration) {
		var base64Decoder = Base64.getDecoder();
		var decodedPublicKey = base64Decoder.decode(configuration.getProperty("keys.public.x509"));
		var decodedPrivateKey = base64Decoder.decode(configuration.getProperty("keys.private.pkcs8"));
		var arbiterPublicKeyUrl = base64Decoder.decode(configuration.getProperty("keys.private.pkcs8"));

		var keyFactory = createKeyFactory();
		var publicKey = getPublicKey(keyFactory, decodedPublicKey);
		var privateKey = getPrivateKey(keyFactory, decodedPrivateKey);
		var arbiterPublicKey = getArbiterPublicKey(configuration, base64Decoder, keyFactory);

		return new DefaultSignatureService(
			Configurator::initializeSignature,
			() -> publicKey,
			() -> privateKey,
			() -> arbiterPublicKey);
	}

	private static Signature initializeSignature() {
		var signatureAlgorithm = "SHA256withRSA";
		try {
			return Signature.getInstance(signatureAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static KeyFactory createKeyFactory() {
		var keyAlgorithm = "RSA";
		try {
			return KeyFactory.getInstance(keyAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static PublicKey getPublicKey(KeyFactory keyFactory, byte[] decodedPublicKey) {
		try {
			return keyFactory.generatePublic(new X509EncodedKeySpec(decodedPublicKey));
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	private static PrivateKey getPrivateKey(KeyFactory keyFactory, byte[] decodedPrivateKey) {
		try {
			return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedPrivateKey));
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	private static PublicKey getArbiterPublicKey(
			Properties configuration,
			Base64.Decoder base64Decoder,
			KeyFactory keyFactory) {
		var arbiterPublicKeyUrl = configuration.getProperty("arbitrary.urls.publickey");
		try {
			var arbiterPublicKeyConnection = new URL(arbiterPublicKeyUrl).openConnection();
			byte[] base64EncodedPublicKey;
			try (var arbiterPublicKeyStream = arbiterPublicKeyConnection.getInputStream()) {
				base64EncodedPublicKey = arbiterPublicKeyStream.readAllBytes();
			}

			var decodedPublicKey = base64Decoder.decode(base64EncodedPublicKey);
			return keyFactory.generatePublic(new X509EncodedKeySpec(decodedPublicKey));
		} catch (IOException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	private static InputStream initializeBlockchainInputStream(String fileName) {
		try {
			return Files.newInputStream(Path.of(fileName), StandardOpenOption.READ);
		} catch (IOException e) {
			return InputStream.nullInputStream();
		}
	}

	private static OutputStream initializeBlockchainOutputStream(String fileName) {
		try {
			return Files.newOutputStream(Path.of(fileName), StandardOpenOption.CREATE);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
