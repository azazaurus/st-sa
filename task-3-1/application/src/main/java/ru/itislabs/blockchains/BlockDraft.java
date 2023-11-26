package ru.itislabs.blockchains;

public class BlockDraft {
	private final byte[] data;
	private final byte[] dataSignature;
	private final byte[] hashSignature;
	private final byte[] previousBlockHash;

	public BlockDraft(byte[] data, byte[] dataSignature, byte[] hashSignature) {
		this.data = data;
		this.dataSignature = dataSignature;
		this.hashSignature = hashSignature;
		this.previousBlockHash = null;
	}

	public BlockDraft(byte[] data, byte[] dataSignature, byte[] hashSignature, byte[] previousBlockHash) {
		this.data = data;
		this.dataSignature = dataSignature;
		this.hashSignature = hashSignature;
		this.previousBlockHash = previousBlockHash;
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
