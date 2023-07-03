package com.prashanth.zoomconnect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Autowired
	private SwaggerConfigProps configProps;

	@Bean
	public OpenAPI zoomConnectOpenAPI() {
		return new OpenAPI().info(new Info().title(configProps.getTitle()).description(configProps.getDescription())
				.version(configProps.getVersion()));
	}
}
