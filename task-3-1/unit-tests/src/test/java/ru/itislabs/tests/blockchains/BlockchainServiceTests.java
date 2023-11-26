package ru.itislabs.tests.blockchains;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.*;
import ru.itislabs.blockchains.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
public class BlockchainServiceTests {
	@Test
	public void appendsBlockAndReturnsAppendedBlock() {
		var blockchainService = BlockchainServiceFactory.create();
		var blockData = "data";

		var appendedBlock = blockchainService.appendBlock(blockData);
		var appendedBlockData = new String(appendedBlock.getData(), BlockchainService.encodingCharset);

		assertThat(appendedBlockData, is(blockData));
	}

	@Test
	public void appendsTwoBlocks() {
		var blockchainService = BlockchainServiceFactory.create();
		var block1 = new BlockDraft(new byte[] { 33, 32 }, new byte[] { 16 }, new byte[] { 64 }, new byte[] { 8 });
		var block2Data = "block2";

		var appendedBlock1 = blockchainService.appendArbitraryBlock(block1);
		var appendedBlock2 = blockchainService.appendBlock(block2Data);

		assertBlocksEqual(appendedBlock1, block1);

		var appendedBlock2Data = new String(appendedBlock2.getData(), BlockchainService.encodingCharset);
		assertThat(appendedBlock2Data, is(block2Data));
	}

	private static void assertBlocksEqual(Block actual, BlockDraft expected) {
		assertThat(Arrays.equals(actual.getData(), expected.getData()), is(true));
		assertThat(Arrays.equals(actual.getDataSignature(), expected.getDataSignature()), is(true));
		assertThat(Arrays.equals(actual.getHashSignature(), expected.getHashSignature()), is(true));
		assertThat(Arrays.equals(actual.getPreviousBlockHash(), expected.getPreviousBlockHash()), is(true));
	}
}
