package ru.itislabs.blockchains;

import java.nio.charset.*;

public class BlockchainService {
	public static final Charset encodingCharset = StandardCharsets.UTF_16;

	private final Blockchain blockchain;
	private final BlockDraftFactory blockDraftFactory;

	public BlockchainService(Blockchain blockchain, BlockDraftFactory blockDraftFactory) {
		this.blockchain = blockchain;
		this.blockDraftFactory = blockDraftFactory;
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
		throw new UnsupportedOperationException();
	}
}
