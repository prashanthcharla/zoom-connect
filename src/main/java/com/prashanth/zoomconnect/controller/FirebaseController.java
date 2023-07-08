package com.prashanth.zoomconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashanth.zoomconnect.service.FirebaseServiceImpl;

@RestController
@RequestMapping(value = "/api")
public class FirebaseController {
	
	@Autowired
	FirebaseServiceImpl firebaseService;

	@PostMapping(value = "/refresh")
	public void refresh() {
		firebaseService.refreshOauthToken();
	}
}
