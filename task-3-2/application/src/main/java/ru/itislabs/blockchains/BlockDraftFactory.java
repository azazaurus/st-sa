package ru.itislabs.blockchains;

import org.apache.commons.codec.*;
import org.apache.commons.codec.binary.*;
import org.json.JSONObject;
import ru.itislabs.hash.*;
import ru.itislabs.signatures.*;
import ru.itislabs.utils.RequestUtil;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.*;
import java.util.Arrays;
import java.util.Properties;

public class BlockDraftFactory {
	private static final Charset timestampCharset = StandardCharsets.ISO_8859_1;

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

		try {
			var json = RequestUtil.get(new URL(configuration.getProperty("arbitrary.urls.sign") + Hex.encodeHexString(hash)));
			var signatureResult = (JSONObject)json.get("timeStampToken");
			var hashSignature = Hex.decodeHex((String)signatureResult.get("signature"));
			var timestamp = ((String)signatureResult.get("ts")).getBytes(timestampCharset);
			return new BlockDraft(data, dataSignature, hashSignature, timestamp, previousBlockHash);
		} catch (MalformedURLException | DecoderException e) {
			throw new RuntimeException(e);
		}
	}
}
