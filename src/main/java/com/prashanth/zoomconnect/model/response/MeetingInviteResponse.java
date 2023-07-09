package com.prashanth.zoomconnect.model.response;

import java.util.List;

import lombok.Getter;

@Getter
public class MeetingInviteResponse {
	String invitation;
	List<String> sip_links;
}
