package ru.itislabs.blockchains;

import ru.itislabs.hash.*;
import ru.itislabs.signatures.*;

import java.nio.charset.*;
import java.util.*;

public class BlockchainService {
	public static final Charset encodingCharset = StandardCharsets.UTF_16;

	private final Blockchain blockchain;
	private final BlockDraftFactory blockDraftFactory;
	private final HashService hashService;
	private final SignatureService signatureService;

	public BlockchainService(
			Blockchain blockchain,
			BlockDraftFactory blockDraftFactory,
			HashService hashService,
			SignatureService signatureService) {
		this.blockchain = blockchain;
		this.blockDraftFactory = blockDraftFactory;
		this.hashService = hashService;
		this.signatureService = signatureService;
	}

	public Block appendBlock(String data) {
		var dataBytes = data.getBytes(encodingCharset);
		var lastBlock = blockchain.getLastBlock();
		var block = blockDraftFactory.create(dataBytes, lastBlock);
		return blockchain.appendBlock(block);
	}

	public Block appendArbitraryBlock(BlockDraft block) {
		return blockchain.appendBlock(block);
	}

	public Block readBlock(int id) {
		return blockchain.getBlock(id);
	}

	public boolean verifyBlock(int id) {
		var blockToVerify = blockchain.getBlock(id);
		var blockHash = hashService.calculateCryptographicHash(blockToVerify);
		var isBlockHashCorrect = signatureService.verify(blockHash, blockToVerify.getHashSignature());
		if (!isBlockHashCorrect)
			return false;

		var isBlockDataCorrect = signatureService.verify(
			blockToVerify.getData(),
			blockToVerify.getDataSignature());
		if (!isBlockDataCorrect)
			return false;

		var previousBlock = blockchain.getPreviousBlock(id);
		if (previousBlock != null) {
			if (blockToVerify.getPreviousBlockHash() == null
					|| blockToVerify.getPreviousBlockHash().length == 0)
				return false;

			var previousBlockHash = hashService.calculateCryptographicHash(previousBlock);
			return Arrays.equals(previousBlockHash, blockToVerify.getPreviousBlockHash());
		}

		return true;
	}

	public BlockchainVerificationResult verifyAll() {
		var allIds = blockchain.getAllIds();
		var correctIds = new ArrayList<Integer>(allIds.length);
		var incorrectIds = new ArrayList<Integer>();
		for (var id : allIds) {
			var verificationResult = verifyBlock(id);
			if (verificationResult)
				correctIds.add(id);
			else
				incorrectIds.add(id);
		}

		return new BlockchainVerificationResult(correctIds, incorrectIds);
	}
}
