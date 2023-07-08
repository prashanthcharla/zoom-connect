package com.prashanth.zoomconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashanth.zoomconnect.service.FirebaseServiceImpl;

@RestController
@RequestMapping(value = "/api/firebase")
public class FirebaseController {

	@Autowired
	FirebaseServiceImpl firebaseService;

	@PostMapping(value = "/oauth-token/refresh")
	public void refresh() {
		firebaseService.refreshOauthToken();
	}
}
