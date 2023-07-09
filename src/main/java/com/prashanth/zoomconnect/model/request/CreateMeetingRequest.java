package com.prashanth.zoomconnect.model.request;

import com.prashanth.zoomconnect.model.MeetingSettings;
import com.prashanth.zoomconnect.model.RecurrenceMeeting;

import lombok.Data;

@Data
public class CreateMeetingRequest {
	private String topic;
    private int type;
    private String start_time;
    private int duration;
    private String schedule_for;
    private String timezone;
    private String password;
    private String agenda;
    private RecurrenceMeeting recurrence;
    private MeetingSettings settings;
}
