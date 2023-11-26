package ru.itislabs.blockchains;

import ru.itislabs.hash.*;
import ru.itislabs.signatures.*;

public class BlockDraftFactory {
	private final HashService hashService;
	private final SignatureService signatureService;

	public BlockDraftFactory(HashService hashService, SignatureService signatureService) {
		this.hashService = hashService;
		this.signatureService = signatureService;
	}

	public BlockDraft create(byte[] data, Block previousBlock) {
		var previousBlockHash = previousBlock != null
			? hashService.calculateCryptographicHash(previousBlock)
			: null;
		var dataSignature = signatureService.sign(data);
		var hash = hashService.calculateCryptographicHash(previousBlockHash, data, dataSignature);
		var hashSignature = signatureService.sign(hash);
		return new BlockDraft(data, dataSignature, hashSignature, previousBlockHash);
	}
}
