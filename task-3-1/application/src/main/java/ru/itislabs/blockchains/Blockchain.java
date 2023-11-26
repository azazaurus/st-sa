package ru.itislabs.blockchains;

import java.util.*;

public class Blockchain {
	private final ArrayList<Block> chain;

	public Blockchain() {
		chain = new ArrayList<>();
	}

	public Blockchain(Collection<Block> chain) {
		this.chain = new ArrayList<>(chain);
	}

	public Block getPreviousBlock(int id) {
		return id > 0 && id < chain.size() ? chain.get(id - 1) : null;
	}

	public Block getBlock(int id) {
		return id >= 0 && id < chain.size() ? chain.get(id) : null;
	}

	public Block getLastBlock() {
		var chainSize = chain.size();
		return chainSize > 0 ? chain.get(chainSize - 1) : null;
	}

	public int[] getAllIds() {
		var ids = new int[chain.size()];
		for (int i = 0; i < ids.length; ++i)
			ids[i] = i;
		return ids;
	}

	public Block appendBlock(BlockDraft block) {
		var newBlock = createBlock(chain.size(), block);
		chain.add(newBlock);
		return newBlock;
	}

	private static Block createBlock(int id, BlockDraft blockDraft) {
		return new Block(
			id,
			blockDraft.getData(),
			blockDraft.getDataSignature(),
			blockDraft.getHashSignature(),
			blockDraft.getPreviousBlockHash());
	}
}
