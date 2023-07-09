package com.prashanth.zoomconnect.model.request;

import lombok.Data;

@Data
public class ListMeetingsRequest {
	String userId;
	String type;
	String from;
	String to;
}
