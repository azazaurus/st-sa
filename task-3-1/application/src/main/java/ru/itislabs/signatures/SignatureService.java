package ru.itislabs.signatures;

public interface SignatureService {
	boolean verify(byte[] dataToVerify, byte[] signatureBody);

	byte[] sign(byte[] dataToSign);
}
