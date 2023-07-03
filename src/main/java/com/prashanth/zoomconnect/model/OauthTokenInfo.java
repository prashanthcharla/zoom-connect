package com.prashanth.zoomconnect.model;

import lombok.Data;

@Data
public class OauthTokenInfo {
	private String accessToken;
	private String tokenType;
	private String scope;
	private String refreshToken;
}
