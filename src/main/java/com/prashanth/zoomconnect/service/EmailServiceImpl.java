package com.prashanth.zoomconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.prashanth.zoomconnect.model.request.EmailRequest;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(EmailRequest email) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(email.getFromMail());
		mailMessage.setTo(email.getToMail());
		mailMessage.setSubject(email.getSubject());
		mailMessage.setText(email.getText());

		javaMailSender.send(mailMessage);
	}

}
