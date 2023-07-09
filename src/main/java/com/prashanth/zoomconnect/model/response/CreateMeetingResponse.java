package com.prashanth.zoomconnect.model.response;

import com.prashanth.zoomconnect.model.MeetingSettings;

import lombok.Data;

@Data
public class CreateMeetingResponse {
	private long id;
	private String uuid;
	private String host_id;
	private String host_email;
	private String topic;
	private int type;
	private String status;
	private String start_time;
	private int duration;
	private String timezone;
	private String agenda;
	private String created_at;
	private String start_url;
	private String join_url;
	private String password;
	private String h323_password;
	private String pstn_password;
	private String encrypted_password;
	private String pmi;
	private MeetingSettings settings;
}
