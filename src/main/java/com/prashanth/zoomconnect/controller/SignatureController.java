package com.prashanth.zoomconnect.controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashanth.zoomconnect.config.ZoomMetadata;
import com.prashanth.zoomconnect.model.SignatureRequest;
import com.prashanth.zoomconnect.service.SignatureService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/signature")
public class SignatureController {

	@Autowired
	SignatureService signatureService;

	@Autowired
	ZoomMetadata zoomMetadata;

	@PostMapping("/generate")
	public ResponseEntity<String> generateSigntaure(@RequestBody SignatureRequest signatureRequest)
			throws InvalidKeyException, NoSuchAlgorithmException {
		long timestamp = System.currentTimeMillis() - 30000;
		String signature = signatureService.generateSignature(zoomMetadata.getClientId(),
				zoomMetadata.getClientSecret(), signatureRequest.getMeetingId(), timestamp, signatureRequest.getRole());
		return new ResponseEntity<>(signature, HttpStatus.OK);
	}
}
