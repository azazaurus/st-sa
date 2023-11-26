package ru.itislabs.hash;

import ru.itislabs.blockchains.*;

import java.security.*;
import java.util.function.*;

public class DefaultHashService implements HashService {
	private final Supplier<MessageDigest> cryptographicHashProvider;

	public DefaultHashService(Supplier<MessageDigest> cryptographicHashProvider) {
		this.cryptographicHashProvider = cryptographicHashProvider;
	}

	@Override
	public byte[] calculateCryptographicHash(Block block) {
		var hashFunction = cryptographicHashProvider.get();
		if (block.getPreviousBlockHash() != null)
			hashFunction.update(block.getPreviousBlockHash());
		hashFunction.update(block.getData());
		hashFunction.update(block.getDataSignature());
		return hashFunction.digest();
	}

	@Override
	public byte[] calculateCryptographicHash(byte[]... byteArraysToHash) {
		var hashFunction = cryptographicHashProvider.get();
		for (var bytesToHash : byteArraysToHash)
			if (bytesToHash != null && bytesToHash.length > 0)
				hashFunction.update(bytesToHash);
		return hashFunction.digest();
	}
}
