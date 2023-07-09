package com.prashanth.zoomconnect.model;

import java.util.List;

import lombok.Data;

@Data
public class MeetingsList {
	String next_page_token;
	int page_count;
	int page_number;
	int page_size;
	int total_records;
	List<MeetingDetails> meetings;
}
