package ru.itislabs;

import org.apache.commons.codec.binary.*;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;

public class Application {
	private static final String configurationFileName = "application.properties";
	private static final String keyAlgorithm = "RSA";
	private static final String signAlgorithm = "SHA256withRSA";

	public static void main(String[] args) throws Exception {
		var configuration = loadConfiguration(configurationFileName, Application.class.getClassLoader());

		var authorName = configuration.getProperty("author.name");

		var keyFactory = KeyFactory.getInstance(keyAlgorithm);

		var privateKeyFileName = Path.of(configuration.getProperty("keys.private.filename"));
		var pemPrivateKey = Files.readAllBytes(privateKeyFileName);
		var privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(pemPrivateKey));

		var publicKeyFileName = Path.of(configuration.getProperty("keys.public.filename"));
		var pemPublicKey = Files.readAllBytes(publicKeyFileName);
		var publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(pemPublicKey));

		var signature = Signature.getInstance(signAlgorithm);
		signature.initSign(privateKey);
		signature.update(authorName.getBytes(StandardCharsets.UTF_8));

		var signatureHex = Hex.encodeHexString(signature.sign());
		var publicKeyHex = Hex.encodeHexString(publicKey.getEncoded());
		var authorInfo = new Formatter()
			.format(
				"{\"autor\":\"%s\",\"sign\":\"%s\",\"publickey\":\"%s\"}",
				authorName,
				signatureHex,
				publicKeyHex)
			.toString();

		var connection = (HttpURLConnection)new URL("http://itislabs.ru/nbc/autor").openConnection();
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Content-Type","application/json; charset=UTF-8");
		connection.setDoOutput(true);
		connection.getOutputStream().write(authorInfo.getBytes(StandardCharsets.UTF_8));

		int responseCode = connection.getResponseCode();
		System.out.println("Response code: " + responseCode);

		var response = new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
		System.out.println(response);
	}

	private static Properties loadConfiguration(String configurationFileName, ClassLoader classLoader) {
		try (var configurationStream = classLoader.getResourceAsStream(configurationFileName)) {
			var configuration = new Properties();
			configuration.load(configurationStream);
			return configuration;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
