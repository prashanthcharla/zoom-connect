package com.prashanth.zoomconnect.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class SignatureServiceImpl implements SignatureService {

	private static final String HMAC_ALGORITHM = "HmacSHA256";

	@Override
	public String generateSignature(String apiKey, String apiSecret, String meetingNumber, long timestamp, int role)
			throws NoSuchAlgorithmException, InvalidKeyException {
		String message = apiKey + meetingNumber + timestamp + role;
		byte[] hmacBytes = this.calculateHmac(message.getBytes(), apiSecret.getBytes());
		String hash = Base64.getEncoder().encodeToString(hmacBytes);
		return apiKey + "." + meetingNumber + "." + timestamp + "." + role + "." + hash;
	}

	private byte[] calculateHmac(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec secretKey = new SecretKeySpec(key, HMAC_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_ALGORITHM);
		mac.init(secretKey);
		return mac.doFinal(data);
	}

}
