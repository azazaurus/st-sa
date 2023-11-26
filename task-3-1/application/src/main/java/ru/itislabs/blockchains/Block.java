package ru.itislabs.blockchains;

public class Block {
	private final int id;
	private final byte[] data;
	private final byte[] dataSignature;
	private final byte[] hashSignature;
	private final byte[] previousBlockHash;

	public Block(int id, byte[] data, byte[] dataSignature, byte[] hashSignature) {
		this.id = id;
		this.data = data;
		this.dataSignature = dataSignature;
		this.hashSignature = hashSignature;
		this.previousBlockHash = null;
	}

	public Block(int id, byte[] data, byte[] dataSignature, byte[] hashSignature, byte[] previousBlockHash) {
		this.id = id;
		this.data = data;
		this.dataSignature = dataSignature;
		this.hashSignature = hashSignature;
		this.previousBlockHash = previousBlockHash;
	}

	public int getId() {
		return id;
	}

	public byte[] getData() {
		return data;
	}

	public byte[] getDataSignature() {
		return dataSignature;
	}

	public byte[] getHashSignature() {
		return hashSignature;
	}

	public byte[] getPreviousBlockHash() {
		return previousBlockHash;
	}
}
