package ru.itislabs.blockchains;

import java.time.Instant;

public class BlockDraft {
	private final byte[] data;
	private final byte[] dataSignature;
	private final byte[] hashSignature;
	private final String timestamp;
	private final byte[] previousBlockHash;

	public BlockDraft(byte[] data, byte[] dataSignature, byte[] hashSignature, String timestamp) {
		this.data = data;
		this.dataSignature = dataSignature;
		this.hashSignature = hashSignature;
		this.timestamp = timestamp;
		this.previousBlockHash = null;
	}

	public BlockDraft(byte[] data, byte[] dataSignature, byte[] hashSignature, String timestamp, byte[] previousBlockHash) {
		this.data = data;
		this.dataSignature = dataSignature;
		this.hashSignature = hashSignature;
		this.timestamp = timestamp;
		this.previousBlockHash = previousBlockHash;
	}

	public String getTimestamp() {
		return timestamp;
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
