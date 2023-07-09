package com.prashanth.zoomconnect.model;

import lombok.Data;

@Data
public class Email {
	private String fromMail;
	private String toMail;
	private String subject;
	private String text;
}
