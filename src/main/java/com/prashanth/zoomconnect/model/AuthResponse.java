package com.prashanth.zoomconnect.model;

import lombok.Data;

@Data
public class AuthResponse {
	String encryptedUserId;
	String encryptedUserName;
	String encryptedApiKey;
}
