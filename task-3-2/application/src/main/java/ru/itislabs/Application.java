package ru.itislabs;

import ru.itislabs.blockchains.*;
import ru.itislabs.configuration.*;
import java.io.*;
import java.util.*;

public class Application {
	public final Properties configuration;

	private static final String usageHelpString = "Available commands:" + System.lineSeparator()
		+ "add <data string>" + System.lineSeparator()
		+ "read <block ID>" + System.lineSeparator()
		+ "verify <block ID>" + System.lineSeparator()
		+ "verify-all" + System.lineSeparator();

	private final Base64.Encoder base64Encoder;
	private final BlockchainService blockchainService;

	public Application(Properties configuration, Base64.Encoder base64Encoder, BlockchainService blockchainService) {
		this.configuration = configuration;
		this.base64Encoder = base64Encoder;
		this.blockchainService = blockchainService;
	}

	public void run() {
		var commandReader = new Scanner(System.in);
		applicationLoop:
		while (true) {
			var commandArguments = commandReader.nextLine().split("\\s", 2);
			var commandType = commandArguments[0];

			switch (commandType) {
				case "add":
					handleAddCommand(commandArguments);
					break;
				case "read":
					handleReadCommand(commandArguments);
					break;
				case "verify":
					handleVerifyCommand(commandArguments);
					break;
				case "verify-all":
					handleVerifyAllCommand(commandArguments);
					break;
				case "exit":
				case "quit":
					break applicationLoop;
				default:
					handleUnknownCommand(commandArguments);
					break;
			}
		}
	}

	private void handleAddCommand(String[] commandArguments) {
		if (commandArguments.length != 2) {
			System.out.println("Usage: add <data string>");
			return;
		}

		var dataToAdd = commandArguments[1];
		var newBlock = blockchainService.appendBlock(dataToAdd);
		System.out.println("Added block with id " + newBlock.getId());
	}

	private void handleReadCommand(String[] commandArguments) {
		if (commandArguments.length != 2) {
			System.out.println("Usage: read <block ID>");
			return;
		}

		Optional<Block> blockResult = tryReadBlock(commandArguments[1]);
		if (blockResult.isEmpty())
			return;

		var block = blockResult.get();
		var blockData = new String(block.getData(), BlockchainService.encodingCharset);
		System.out.println("Block data: " + blockData);

		var blockDataSignature = base64Encoder.encodeToString(block.getDataSignature());
		System.out.println("Base64 block data signature: " + blockDataSignature);

		var blockHashSignature = base64Encoder.encodeToString(block.getHashSignature());
		System.out.println("Base64 block hash signature: " + blockHashSignature);

		var previousBlockHash = block.getPreviousBlockHash() != null
			? base64Encoder.encodeToString(block.getPreviousBlockHash())
			: "<none>";
		System.out.println("Base64 previous block hash: " + previousBlockHash);
	}

	private void handleVerifyCommand(String[] commandArguments) {
		if (commandArguments.length != 2) {
			System.out.println("Usage: verify <block ID>");
			return;
		}

		Optional<Block> blockResult = tryReadBlock(commandArguments[1]);
		if (blockResult.isEmpty())
			return;

		var blockId = blockResult.get().getId();
		var verificationResult = blockchainService.verifyBlock(blockId);
		System.out.println(
			verificationResult
				? "Block is correct"
				: "Block is not correct");
	}

	private void handleVerifyAllCommand(String[] commandArguments) {
		if (commandArguments.length > 1) {
			System.out.println("Usage: verify-all");
			return;
		}

		var verificationResult = blockchainService.verifyAll();
		if (verificationResult.correctBlockIds.size() + verificationResult.incorrectBlockIds.size() == 0)
			System.out.println("No blocks are found");
		else if (verificationResult.incorrectBlockIds.isEmpty())
			System.out.println("All blocks are correct");
		else {
			var incorrectBlockIds = verificationResult.incorrectBlockIds.stream()
				.map(Object::toString)
				.toArray(String[]::new);
			System.out.println("Incorrect block IDs: " + String.join(", ", incorrectBlockIds));
		}
	}

	private void handleUnknownCommand(String[] commandArguments) {
		System.out.println("Unknown command: " + commandArguments[0]);
		System.out.println(usageHelpString);
	}

	private Optional<Block> tryReadBlock(String blockIdString) {
		var blockIdResult = tryParseBlockId(blockIdString);
		if (blockIdResult.isEmpty())
			return Optional.empty();

		int blockId = blockIdResult.get();
		var block = blockchainService.readBlock(blockId);
		if (block == null) {
			System.out.println("Block " + blockId + " is not found");
			return Optional.empty();
		}

		return Optional.of(block);
	}

	public static void main(String[] args) throws IOException {
		var configuration = Configurator.readConfiguration(Application.class.getClassLoader());
		var blockchainService = Configurator.initializeBlockchainService(configuration);

		var application = new Application(configuration, Base64.getEncoder(), blockchainService);
		application.run();
	}

	private static Optional<Integer> tryParseBlockId(String input) {
		try {
			return Optional.of(Integer.parseInt(input));
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid block ID: \"" + input + "\"");
			return Optional.empty();
		}
	}
}
