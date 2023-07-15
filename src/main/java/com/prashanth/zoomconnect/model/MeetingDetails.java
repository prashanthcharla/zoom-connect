package com.prashanth.zoomconnect.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MeetingDetails {
	String agenda;
	LocalDateTime created_at;
	int duration;
	String host_id;
	long id;
	String join_url;
	String pmi;
	LocalDateTime start_time;
	String timezone;
	String topic;
	int type;
	String uuid;
}
