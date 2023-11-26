package ru.itislabs.tests.blockchains;

import ru.itislabs.blockchains.*;
import ru.itislabs.tests.hash.*;
import ru.itislabs.tests.signatures.*;

public final class BlockchainServiceFactory {
	private BlockchainServiceFactory() {
	}

	public static BlockchainService create() {
		var hashService = DefaultHashServiceFactory.create();
		var signatureService = DefaultSignatureServiceFactory.create();
		return new BlockchainService(
			new Blockchain(),
			new BlockDraftFactory(hashService, signatureService),
			hashService,
			signatureService);
	}
}
