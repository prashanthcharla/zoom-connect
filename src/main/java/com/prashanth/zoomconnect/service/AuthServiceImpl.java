package com.prashanth.zoomconnect.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prashanth.zoomconnect.config.ZoomMetadata;
import com.prashanth.zoomconnect.model.AuthResponse;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private ZoomMetadata zoomMetadata;

	private static final String SIGNATURE_ENCR_ALGORITHM = "HmacSHA256";

	@Override
	public String generateSignature(String apiKey, String apiSecret, String meetingNumber, long timestamp, int role)
			throws NoSuchAlgorithmException, InvalidKeyException {
		String message = apiKey + meetingNumber + timestamp + role;
		byte[] hmacBytes = this.calculateHmac(message.getBytes(), apiSecret.getBytes());
		String hash = Base64.getEncoder().encodeToString(hmacBytes);
		return apiKey + "." + meetingNumber + "." + timestamp + "." + role + "." + hash;
	}

	@Override
	public AuthResponse getApiKey() {
		AuthResponse apiKeyResponse = new AuthResponse();
		apiKeyResponse.setEncryptedUserId(getBase64EncodedString(zoomMetadata.getUserId()));
		apiKeyResponse.setEncryptedUserName(getBase64EncodedString(zoomMetadata.getUserName()));
		apiKeyResponse.setEncryptedApiKey(getBase64EncodedString(zoomMetadata.getClientId()));
		return apiKeyResponse;
	}

	private byte[] calculateHmac(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec secretKey = new SecretKeySpec(key, SIGNATURE_ENCR_ALGORITHM);
		Mac mac = Mac.getInstance(SIGNATURE_ENCR_ALGORITHM);
		mac.init(secretKey);
		return mac.doFinal(data);
	}

	private String getBase64EncodedString(String text) {
		return Base64.getEncoder().encodeToString(text.getBytes());
	}

}
