package com.prashanth.zoomconnect.model;

import lombok.Data;

@Data
public class OauthTokenInfo {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String scope;
}
