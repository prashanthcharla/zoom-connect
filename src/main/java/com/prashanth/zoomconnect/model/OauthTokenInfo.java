package com.prashanth.zoomconnect.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OauthTokenInfo {
	private String access_token;
	private String token_type;
	private BigDecimal expires_in;
	private String scope;
}
