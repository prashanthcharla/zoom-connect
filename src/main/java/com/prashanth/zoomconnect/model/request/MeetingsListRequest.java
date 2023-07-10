package com.prashanth.zoomconnect.model.request;

import lombok.Data;

@Data
public class MeetingsListRequest {
	String userId;
	String type;
	String from;
	String to;
}
