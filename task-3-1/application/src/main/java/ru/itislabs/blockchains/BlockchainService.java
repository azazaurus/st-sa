package ru.itislabs.blockchains;

public class BlockchainService {
	private final Blockchain blockchain;

	public BlockchainService(Blockchain blockchain) {
		this.blockchain = blockchain;
	}

	public Block appendBlock(String data) {
		throw new UnsupportedOperationException();
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
