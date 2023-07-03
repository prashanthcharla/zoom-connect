package com.prashanth.zoomconnect.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "swagger.metadata")
public class SwaggerConfigProps {
	String title;
	String description;
	String version;
}
