package com.prashanth.zoomconnect.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.prashanth.zoomconnect.model.AuthResponse;

public interface AuthService {
	public AuthResponse getApiKey();

	public String generateSignature(String apiKey, String apiSecret, String meetingNumber, long timestamp, int role)
			throws NoSuchAlgorithmException, InvalidKeyException;
}
