package com.prashanth.zoomconnect.service;

import java.util.Optional;

import com.prashanth.zoomconnect.model.CreateMeetingRequest;
import com.prashanth.zoomconnect.model.CreateMeetingResponse;
import com.prashanth.zoomconnect.model.MeetingInviteResponse;

public interface MeetingService {
	Optional<CreateMeetingResponse> createMeeting(CreateMeetingRequest createMeetingRequest);
	Optional<String> getMeetings(String userId, String type);
	Optional<MeetingInviteResponse> getMeetingInvite(long meetingId);
	Optional<Boolean> deleteMeeting(long meetingId);
}
