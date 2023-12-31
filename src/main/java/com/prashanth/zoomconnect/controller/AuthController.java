package com.prashanth.zoomconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashanth.zoomconnect.config.ZoomMetadata;
import com.prashanth.zoomconnect.model.AuthResponse;
import com.prashanth.zoomconnect.service.AuthService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@Autowired
	ZoomMetadata zoomMetadata;

	@GetMapping(value = "/getAuthCred")
	public ResponseEntity<AuthResponse> getApiKey() {
		AuthResponse apiKey = authService.getApiKey();
		if (apiKey != null) {
			return new ResponseEntity<>(apiKey, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
