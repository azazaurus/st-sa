package ru.itislabs.blockchains;

import org.json.JSONObject;
import ru.itislabs.hash.*;
import ru.itislabs.signatures.*;
import ru.itislabs.utils.RequestUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Properties;

public class BlockDraftFactory {
	private final HashService hashService;
	private final SignatureService signatureService;
	public final Properties configuration;

	public BlockDraftFactory(Properties configuration, HashService hashService, SignatureService signatureService) {
		this.configuration = configuration;
		this.hashService = hashService;
		this.signatureService = signatureService;
	}

	public BlockDraft create(byte[] data, Block previousBlock) {
		var previousBlockHash = previousBlock != null
			? hashService.calculateCryptographicHash(previousBlock)
			: null;
		var dataSignature = signatureService.sign(data);
		var hash = hashService.calculateCryptographicHash(previousBlockHash, data, dataSignature);
		JSONObject json = null;
		try {
			json = RequestUtil.get(new URL(configuration.getProperty("arbitrary-api-url") + Arrays.toString(hash)));
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		var hashSignature = json.get("signature").toString().getBytes(StandardCharsets.UTF_8);
		String timestamp = json.get("ts").toString();
		return new BlockDraft(data, dataSignature, hashSignature, timestamp, previousBlockHash);
	}
}
