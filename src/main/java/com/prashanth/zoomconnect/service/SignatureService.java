package com.prashanth.zoomconnect.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface SignatureService {
	public String generateSignature(String apiKey, String apiSecret, String meetingNumber, long timestamp, int role) throws NoSuchAlgorithmException, InvalidKeyException;
}
