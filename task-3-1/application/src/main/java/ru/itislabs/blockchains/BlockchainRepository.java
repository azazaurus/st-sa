package ru.itislabs.blockchains;

import com.google.protobuf.*;
import ru.itislabs.blockchains.dto.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class BlockchainRepository {
	private final Supplier<InputStream> inputStreamProvider;
	private final Supplier<OutputStream> outputStreamProvider;

	public BlockchainRepository(
			Supplier<InputStream> inputStreamProvider,
			Supplier<OutputStream> outputStreamProvider) {
		this.inputStreamProvider = inputStreamProvider;
		this.outputStreamProvider = outputStreamProvider;
	}

	public Blockchain read() {
		try (var inputStream = inputStreamProvider.get()) {
			var binaryBlockchain = inputStream.readAllBytes();
			if (binaryBlockchain.length == 0)
				return null;

			var blockchainDto = BlockchainDto.parseFrom(binaryBlockchain);
			return convertToModel(blockchainDto);
		} catch (IOException e) {
			return null;
		}
	}

	public void save(Blockchain blockchain) {
		try (var outputStream = outputStreamProvider.get()) {
			var blockchainDto = convertToDto(blockchain);
			var binaryBlockchain = blockchainDto.toByteArray();
			outputStream.write(binaryBlockchain);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Blockchain convertToModel(BlockchainDto blockchainDto) {
		if (blockchainDto.getChainCount() == 0)
			return new Blockchain();

		var blocks = blockchainDto.getChainList().stream()
			.sorted(Comparator.comparingInt(BlockDto::getId))
			.toArray(BlockDto[]::new);
		var chain = new ArrayList<Block>(blocks[blocks.length - 1].getId() + 1);
		for (var blockDto : blocks) {
			if (blockDto.getId() < 0)
				throw new RuntimeException(
					"Block must have non-negative ID, but " + blockDto.getId() + " was found");

			var block = new Block(
				blockDto.getId(),
				blockDto.getData().toByteArray(),
				blockDto.getDataSignature().toByteArray(),
				blockDto.getHashSignature().toByteArray(),
				blockDto.hasPreviousBlockHash()
					? blockDto.getPreviousBlockHash().toByteArray()
					: null);
			extend(chain, blockDto.getId());
			chain.add(block);
		}

		return new Blockchain(chain);
	}

	private static BlockchainDto convertToDto(Blockchain blockchain) {
		var blockchainDto = BlockchainDto.newBuilder();
		for (var blockId : blockchain.getAllIds()) {
			var block = blockchain.getBlock(blockId);
			var blockDtoBuilder = BlockDto.newBuilder()
				.setId(blockId)
				.setData(ByteString.copyFrom(block.getData()))
				.setDataSignature(ByteString.copyFrom(block.getDataSignature()))
				.setHashSignature(ByteString.copyFrom(block.getHashSignature()));
			if (block.getPreviousBlockHash() != null)
				blockDtoBuilder.setPreviousBlockHash(ByteString.copyFrom(block.getPreviousBlockHash()));

			var blockDto = blockDtoBuilder.build();
			blockchainDto.addChain(blockDto);
		}

		return blockchainDto.build();
	}

	private static <T> void extend(List<T> list, int newSize) {
		for (int currentSize = list.size(); currentSize < newSize; ++currentSize)
			list.add(null);
	}
}
