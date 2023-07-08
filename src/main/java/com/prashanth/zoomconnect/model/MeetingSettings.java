package com.prashanth.zoomconnect.model;

import lombok.Data;

@Data
public class MeetingSettings {
	private boolean host_video;
	private boolean participant_video;
	private boolean cn_meeting;
	private boolean in_meeting;
	private boolean join_before_host;
	private boolean mute_upon_entry;
	private boolean watermark;
	private boolean use_pmi;
	private int approval_type;
	private int registration_type;
	private String audio;
	private String auto_recording;
	private boolean enforce_login;
	private String enforce_login_domains;
	private String alternative_hosts;
	private String[] global_dial_in_countries;
	private boolean registrants_email_notification;
}
