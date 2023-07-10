package com.prashanth.zoomconnect.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashanth.zoomconnect.config.ZoomMetadata;
import com.prashanth.zoomconnect.model.AuthResponse;
import com.prashanth.zoomconnect.model.SignatureRequest;
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

	@PostMapping(value = "/signature/generate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> generateSigntaure(@RequestBody SignatureRequest signatureRequest)
			throws InvalidKeyException, NoSuchAlgorithmException {
		long timestamp = System.currentTimeMillis() - 30000;
		String signature = authService.generateSignature(zoomMetadata.getClientId(), zoomMetadata.getClientSecret(),
				signatureRequest.getMeetingId(), timestamp, signatureRequest.getRole());
		return new ResponseEntity<>(signature, HttpStatus.OK);
	}
}
