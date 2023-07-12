package com.prashanth.zoomconnect.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prashanth.zoomconnect.config.ZoomMetadata;
import com.prashanth.zoomconnect.model.AuthResponse;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private ZoomMetadata zoomMetadata;

	@Override
	public AuthResponse getApiKey() {
		AuthResponse apiKeyResponse = new AuthResponse();
		apiKeyResponse.setEncryptedUserId(getBase64EncodedString(zoomMetadata.getUserId()));
		apiKeyResponse.setEncryptedUserName(getBase64EncodedString(zoomMetadata.getUserName()));
		apiKeyResponse.setEncryptedApiKey(getBase64EncodedString(zoomMetadata.getClientId()));
		apiKeyResponse.setEncryptedApiSecret(getBase64EncodedString(zoomMetadata.getClientSecret()));
		return apiKeyResponse;
	}

	private String getBase64EncodedString(String text) {
		return Base64.getEncoder().encodeToString(text.getBytes());
	}

}
