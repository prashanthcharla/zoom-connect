package com.prashanth.zoomconnect.model;

import lombok.Data;

@Data
public class SignatureRequest {
	private String meetingId;
	private int role;
}
