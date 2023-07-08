package com.prashanth.zoomconnect.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "zoom.metadata")
public class ZoomMetadata {
	String serverUrl;
	String userId;
	String clientId;
	String clientSecret;
	String oauthTokenStorageCollection;
	String oauthTokenStorageDocument;
	String createdMeetingsStorageCollection;
}
