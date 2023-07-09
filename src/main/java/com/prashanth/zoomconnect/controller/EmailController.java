package com.prashanth.zoomconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashanth.zoomconnect.model.request.EmailRequest;
import com.prashanth.zoomconnect.service.EmailService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/email")
public class EmailController {

	@Autowired
	EmailService emailService;

	@PostMapping("/send")
	public void sendEmail(@RequestBody EmailRequest email) {
		emailService.sendEmail(email);
	}
}
