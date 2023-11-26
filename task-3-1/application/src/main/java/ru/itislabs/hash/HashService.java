package ru.itislabs.hash;

import ru.itislabs.blockchains.*;

public interface HashService {
	byte[] calculateCryptographicHash(Block block);

	byte[] calculateCryptographicHash(byte[]... byteArraysToHash);
}
