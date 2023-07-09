package com.prashanth.zoomconnect.model.request;

import lombok.Data;

@Data
public class EmailRequest {
	private String fromMail;
	private String toMail;
	private String subject;
	private String text;
}
