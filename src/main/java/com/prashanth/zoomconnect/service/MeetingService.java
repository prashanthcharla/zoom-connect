package com.prashanth.zoomconnect.service;

import java.util.Optional;

import com.prashanth.zoomconnect.model.CreateMeetingRequest;
import com.prashanth.zoomconnect.model.CreateMeetingResponse;
import com.prashanth.zoomconnect.model.ListMeetingsRequest;
import com.prashanth.zoomconnect.model.MeetingInviteResponse;
import com.prashanth.zoomconnect.model.MeetingsList;

public interface MeetingService {
	Optional<CreateMeetingResponse> createMeeting(CreateMeetingRequest createMeetingRequest);
	Optional<MeetingsList> getMeetings(ListMeetingsRequest request);
	Optional<MeetingInviteResponse> getMeetingInvite(long meetingId);
	Optional<Boolean> deleteMeeting(long meetingId);
}
