package com.prashanth.zoomconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.prashanth.zoomconnect.config.SwaggerConfigProps;
import com.prashanth.zoomconnect.config.ZoomMetadata;

@SpringBootApplication
@EnableConfigurationProperties({ SwaggerConfigProps.class, ZoomMetadata.class })
public class ZoomConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZoomConnectApplication.class, args);
	}

}
