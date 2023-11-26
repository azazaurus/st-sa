package ru.itislabs.blockchains;

import java.util.*;

public class BlockchainVerificationResult {
	public final Collection<Integer> correctBlockIds;
	public final Collection<Integer> incorrectBlockIds;

	public BlockchainVerificationResult(
			Collection<Integer> correctBlockIds,
			Collection<Integer> incorrectBlockIds) {
		this.correctBlockIds = correctBlockIds;
		this.incorrectBlockIds = incorrectBlockIds;
	}
}
