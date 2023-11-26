package ru.itislabs.tests.blockchains;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import ru.itislabs.blockchains.*;

import java.util.*;
import java.util.stream.*;

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

	@Test
	public void appendsAndVerifiesBlock() {
		var blockchainService = BlockchainServiceFactory.create();
		var blockData = "data";

		var appendedBlock = blockchainService.appendBlock(blockData);
		var isBlockCorrect = blockchainService.verifyBlock(appendedBlock.getId());

		assertThat(isBlockCorrect, is(true));
	}

	@Test
	public void appendsAndVerifiesTwoBlocks() {
		var blockchainService = BlockchainServiceFactory.create();
		var block1Data = "block1";
		var block2Data = "block2";

		var appendedBlock1 = blockchainService.appendBlock(block1Data);
		var appendedBlock2 = blockchainService.appendBlock(block2Data);

		var isBlock1Correct = blockchainService.verifyBlock(appendedBlock1.getId());
		assertThat(isBlock1Correct, is(true));

		var isBlock2Correct = blockchainService.verifyBlock(appendedBlock2.getId());
		assertThat(isBlock2Correct, is(true));
	}

	@ParameterizedTest
	@MethodSource("verifiesCorrectBlockTestCaseSource")
	public void verifiesCorrectBlock(BlockDraft correctBlockDraft) {
		var blockchainService = BlockchainServiceFactory.create();

		var correctBlock = blockchainService.appendArbitraryBlock(correctBlockDraft);

		var isBlockCorrect = blockchainService.verifyBlock(correctBlock.getId());
		assertThat(isBlockCorrect, is(true));
	}

	public static Stream<Arguments> verifiesCorrectBlockTestCaseSource() {
		return Stream.of(
			Arguments.of(
				new BlockDraft(
					new byte[] { 33, 32 },
					new byte[] { 82, -25, 73, 119, 119, -105, -90, 62, -2, 77, 80, 92, 88, -95, 16, 52, 106, -43, 116, -24, -27, -90, 76, 49, 119, -87, -45, 58, -4, -119, 83, -64, -107, -77, -65, 55, 27, 49, -97, -66, 108, -29, 37, -113, 5, -15, 122, -75, 92, -44, -123, 88, 102, -101, -80, -9, 8, -114, 2, -108, 127, -11, -65, 109, 40, -3, -60, 78, -97, 96, -65, -16, -106, 55, 105, 19, -10, 82, 43, -116, -14, 14, 103, -113, -94, -60, 62, -28, -51, -97, 50, 47, 48, 0, -50, 38, -115, 59, -65, -105, -40, 104, 15, 116, -113, 25, 73, 41, -10, -125, -31, 74, -11, -60, 37, 50, -21, 34, -77, 13, -32, -3, -91, -104, -101, -87, 22, -57 },
					new byte[] { 103, 124, 82, -7, 49, -44, -126, -20, 125, 89, 6, -29, -117, 106, 30, -9, -50, 32, -18, -106, 95, -17, 122, 60, 59, 78, 6, 74, -27, 84, -88, -86, -1, -109, 33, 95, -80, 83, 7, 16, -20, 64, 47, -72, 69, -19, 31, 61, -34, 69, -95, -55, -104, -17, 85, -51, 11, 79, 6, 103, -88, 92, 75, -98, -97, -3, -10, 104, -78, 114, -88, -2, -118, 112, 119, 103, 118, 122, 121, -27, 79, -39, -89, 37, -98, -52, -101, -58, -88, -84, 45, 74, 45, -56, 71, 61, -31, -5, 97, -32, -96, 4, 44, -66, 98, -77, 44, 99, -83, -33, -28, 9, 20, 73, -117, -89, 109, -72, 113, -37, 113, 8, 31, -126, 116, -6, -128, 65 })),
			Arguments.of(
				new BlockDraft(
					new byte[] { 33, 32 },
					new byte[] { 82, -25, 73, 119, 119, -105, -90, 62, -2, 77, 80, 92, 88, -95, 16, 52, 106, -43, 116, -24, -27, -90, 76, 49, 119, -87, -45, 58, -4, -119, 83, -64, -107, -77, -65, 55, 27, 49, -97, -66, 108, -29, 37, -113, 5, -15, 122, -75, 92, -44, -123, 88, 102, -101, -80, -9, 8, -114, 2, -108, 127, -11, -65, 109, 40, -3, -60, 78, -97, 96, -65, -16, -106, 55, 105, 19, -10, 82, 43, -116, -14, 14, 103, -113, -94, -60, 62, -28, -51, -97, 50, 47, 48, 0, -50, 38, -115, 59, -65, -105, -40, 104, 15, 116, -113, 25, 73, 41, -10, -125, -31, 74, -11, -60, 37, 50, -21, 34, -77, 13, -32, -3, -91, -104, -101, -87, 22, -57 },
					new byte[] { 10, 32, -81, 86, 101, 91, -95, -3, -127, -23, 41, -59, -100, 125, -127, -102, -52, -106, 116, 32, 54, -92, 26, 2, 60, -31, 7, 67, -64, 22, 70, 56, 0, -49, -94, -31, -40, -74, 70, 88, 50, -69, 13, 107, -59, 9, 90, 46, 112, 92, 102, -12, -124, 49, 110, -49, -13, -65, 74, 31, -97, -21, -101, -91, 45, -55, -43, -110, 70, 53, -113, 88, 55, 36, -76, -56, -81, 5, 39, 115, 126, -66, 6, 94, 15, 83, -10, 102, -29, 40, -47, 118, -128, 106, -108, -29, -49, -20, 34, -25, 88, 19, -21, 50, -47, 41, 107, -90, -27, -93, 48, -86, -25, 19, 70, -44, 67, 45, -113, -126, -10, 39, -80, 48, -6, 88, 1, 32 },
					new byte[] { 0 })));
	}

	@ParameterizedTest
	@MethodSource("notVerifiesIncorrectBlockTestCaseSource")
	public void notVerifiesIncorrectBlock(BlockDraft incorrectBlockDraft) {
		var blockchainService = BlockchainServiceFactory.create();

		var incorrectBlock = blockchainService.appendArbitraryBlock(incorrectBlockDraft);

		var isBlockCorrect = blockchainService.verifyBlock(incorrectBlock.getId());
		assertThat(isBlockCorrect, is(false));
	}

	public static Stream<Arguments> notVerifiesIncorrectBlockTestCaseSource() {
		return Stream.of(
			Arguments.of(
				new BlockDraft(new byte[] { 33, 32 }, new byte[] { 16 }, new byte[] { 64 })),
			Arguments.of(
				new BlockDraft(
					new byte[] { 33, 32 },
					new byte[] { 82, -25, 73, 119, 119, -105, -90, 62, -2, 77, 80, 92, 88, -95, 16, 52, 106, -43, 116, -24, -27, -90, 76, 49, 119, -87, -45, 58, -4, -119, 83, -64, -107, -77, -65, 55, 27, 49, -97, -66, 108, -29, 37, -113, 5, -15, 122, -75, 92, -44, -123, 88, 102, -101, -80, -9, 8, -114, 2, -108, 127, -11, -65, 109, 40, -3, -60, 78, -97, 96, -65, -16, -106, 55, 105, 19, -10, 82, 43, -116, -14, 14, 103, -113, -94, -60, 62, -28, -51, -97, 50, 47, 48, 0, -50, 38, -115, 59, -65, -105, -40, 104, 15, 116, -113, 25, 73, 41, -10, -125, -31, 74, -11, -60, 37, 50, -21, 34, -77, 13, -32, -3, -91, -104, -101, -87, 22, -57 },
					new byte[] { 64 })),
			Arguments.of(
				new BlockDraft(
					new byte[] { 33, 32 },
					new byte[] { 16 },
					new byte[] { 107, 13, 1, -92, 92, 122, -55, 34, -86, 28, -108, 6, 124, -28, 64, -52, -27, 42, 112, -52, -6, 108, 41, 1, 41, 44, 66, -73, 41, -61, -103, -122, -108, 23, -8, -90, -92, 124, -71, 108, -6, 41, -66, 9, 93, -95, -35, 69, 78, -37, 26, 91, -24, -108, 12, -109, 69, -71, 127, 59, 100, 57, -125, -13, 42, -84, 84, -31, 20, -105, -48, 75, 96, -30, 83, -64, 100, 5, -31, 13, -33, 1, 95, -8, -66, -69, -8, 60, 59, 92, 16, 60, -98, 47, 72, -45, 32, -107, -93, -12, -6, -21, 108, 80, -118, -52, -62, 21, -111, -9, -25, -67, -42, 40, 43, 119, -106, 119, 107, 8, 3, 52, 6, -98, -31, 33, 54, 2 })));
	}

	@ParameterizedTest
	@MethodSource("notVerifiesIncorrectSecondBlockTestCaseSource")
	public void notVerifiesIncorrectSecondBlock(BlockDraft incorrectBlock) {
		var blockchainService = BlockchainServiceFactory.create();
		var correctBlockData = "correctBlock";

		var block1 = blockchainService.appendBlock(correctBlockData);
		var block2 = blockchainService.appendArbitraryBlock(incorrectBlock);

		var isBlock1Correct = blockchainService.verifyBlock(block1.getId());
		assertThat(isBlock1Correct, is(true));

		var isBlock2Correct = blockchainService.verifyBlock(block2.getId());
		assertThat(isBlock2Correct, is(false));
	}

	public static Stream<Arguments> notVerifiesIncorrectSecondBlockTestCaseSource() {
		return Stream.of(
			Arguments.of(
				new BlockDraft(
					new byte[] { 33, 32 },
					new byte[] { 82, -25, 73, 119, 119, -105, -90, 62, -2, 77, 80, 92, 88, -95, 16, 52, 106, -43, 116, -24, -27, -90, 76, 49, 119, -87, -45, 58, -4, -119, 83, -64, -107, -77, -65, 55, 27, 49, -97, -66, 108, -29, 37, -113, 5, -15, 122, -75, 92, -44, -123, 88, 102, -101, -80, -9, 8, -114, 2, -108, 127, -11, -65, 109, 40, -3, -60, 78, -97, 96, -65, -16, -106, 55, 105, 19, -10, 82, 43, -116, -14, 14, 103, -113, -94, -60, 62, -28, -51, -97, 50, 47, 48, 0, -50, 38, -115, 59, -65, -105, -40, 104, 15, 116, -113, 25, 73, 41, -10, -125, -31, 74, -11, -60, 37, 50, -21, 34, -77, 13, -32, -3, -91, -104, -101, -87, 22, -57 },
					new byte[] { 10, 32, -81, 86, 101, 91, -95, -3, -127, -23, 41, -59, -100, 125, -127, -102, -52, -106, 116, 32, 54, -92, 26, 2, 60, -31, 7, 67, -64, 22, 70, 56, 0, -49, -94, -31, -40, -74, 70, 88, 50, -69, 13, 107, -59, 9, 90, 46, 112, 92, 102, -12, -124, 49, 110, -49, -13, -65, 74, 31, -97, -21, -101, -91, 45, -55, -43, -110, 70, 53, -113, 88, 55, 36, -76, -56, -81, 5, 39, 115, 126, -66, 6, 94, 15, 83, -10, 102, -29, 40, -47, 118, -128, 106, -108, -29, -49, -20, 34, -25, 88, 19, -21, 50, -47, 41, 107, -90, -27, -93, 48, -86, -25, 19, 70, -44, 67, 45, -113, -126, -10, 39, -80, 48, -6, 88, 1, 32 },
					new byte[] { 0 })));
	}

	private static void assertBlocksEqual(Block actual, BlockDraft expected) {
		assertThat(Arrays.equals(actual.getData(), expected.getData()), is(true));
		assertThat(Arrays.equals(actual.getDataSignature(), expected.getDataSignature()), is(true));
		assertThat(Arrays.equals(actual.getHashSignature(), expected.getHashSignature()), is(true));
		assertThat(Arrays.equals(actual.getPreviousBlockHash(), expected.getPreviousBlockHash()), is(true));
	}
}
