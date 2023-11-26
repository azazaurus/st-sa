package ru.itislabs.blockchains;

import ru.itislabs.hash.*;
import ru.itislabs.signatures.*;
import ru.itislabs.utils.ByteArrayUtil;

import java.nio.charset.*;
import java.util.*;

public class BlockchainService {
	public static final Charset encodingCharset = StandardCharsets.UTF_16;

	private final BlockchainRepository blockchainRepository;
	private final BlockDraftFactory blockDraftFactory;
	private final HashService hashService;
	private final SignatureService signatureService;

	private Blockchain blockchain;

	public BlockchainService(
			BlockchainRepository blockchainRepository,
			BlockDraftFactory blockDraftFactory,
			HashService hashService,
			SignatureService signatureService) {
		this.blockchainRepository = blockchainRepository;
		this.blockDraftFactory = blockDraftFactory;
		this.hashService = hashService;
		this.signatureService = signatureService;
	}

	public Block appendBlock(String data) {
		var dataBytes = data.getBytes(encodingCharset);
		var lastBlock = getBlockchain().getLastBlock();
		var block = blockDraftFactory.create(dataBytes, lastBlock);
		var newBlock = getBlockchain().appendBlock(block);

		blockchainRepository.save(blockchain);

		return newBlock;
	}

	public Block appendArbitraryBlock(BlockDraft block) {
		var newBlock = getBlockchain().appendBlock(block);

		blockchainRepository.save(blockchain);

		return newBlock;
	}

	public Block readBlock(int id) {
		return getBlockchain().getBlock(id);
	}

	public boolean verifyBlock(int id) {
		var blockToVerify = getBlockchain().getBlock(id);
		var blockHash = hashService.calculateCryptographicHash(blockToVerify);
		var concatenatedTimestampAndHashBytes = ByteArrayUtil.concat(blockToVerify.getTimestamp().getBytes(), blockHash);
		var isBlockHashSignatureCorrect = signatureService.verify(concatenatedTimestampAndHashBytes, blockToVerify.getHashSignature());
		if (!isBlockHashSignatureCorrect)
			return false;

		var isBlockDataCorrect = signatureService.verify(
			blockToVerify.getData(),
			blockToVerify.getDataSignature());
		if (!isBlockDataCorrect)
			return false;

		var previousBlock = getBlockchain().getPreviousBlock(id);
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
		var allIds = getBlockchain().getAllIds();
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

	public void reloadBlockchain() {
		blockchain = blockchainRepository.read();
	}
	
	private Blockchain getBlockchain() {
		if (blockchain == null) {
			blockchain = blockchainRepository.read();
			if (blockchain == null)
				blockchain = new Blockchain();
		}

		return blockchain;
	}
}
