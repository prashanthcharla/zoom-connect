package com.prashanth.zoomconnect.service;

import java.util.Optional;

import com.prashanth.zoomconnect.model.CreateMeetingRequest;
import com.prashanth.zoomconnect.model.CreateMeetingResponse;

public interface MeetingService {
	Optional<CreateMeetingResponse> createMeeting(CreateMeetingRequest createMeetingRequest);
	Optional<String> getMeetings(String userId, String type);
	Optional<String> getMeetingInvite(long meetingId);
	Optional<Boolean> deleteMeeting(long meetingId);
}
