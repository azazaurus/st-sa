package ru.itislabs.blockchains;

import java.time.Instant;

public class Block {
	private final int id;
	private final byte[] data;
	private final byte[] dataSignature;
	private final byte[] hashSignature;
	private final String timestamp;
	private final byte[] previousBlockHash;

	public Block(int id, byte[] data, byte[] dataSignature, byte[] hashSignature, String timestamp) {
		this.id = id;
		this.data = data;
		this.dataSignature = dataSignature;
		this.hashSignature = hashSignature;
		this.timestamp = timestamp;
		this.previousBlockHash = null;
	}

	public Block(int id, byte[] data, byte[] dataSignature, byte[] hashSignature, String timestamp, byte[] previousBlockHash) {
		this.id = id;
		this.data = data;
		this.dataSignature = dataSignature;
		this.hashSignature = hashSignature;
		this.timestamp = timestamp;
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

	public String getTimestamp() {
		return timestamp;
	}

	public byte[] getPreviousBlockHash() {
		return previousBlockHash;
	}
}
