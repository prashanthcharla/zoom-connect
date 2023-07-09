package com.prashanth.zoomconnect.service;

import java.util.Optional;

import com.prashanth.zoomconnect.model.MeetingsList;
import com.prashanth.zoomconnect.model.request.CreateMeetingRequest;
import com.prashanth.zoomconnect.model.request.ListMeetingsRequest;
import com.prashanth.zoomconnect.model.response.CreateMeetingResponse;
import com.prashanth.zoomconnect.model.response.MeetingInviteResponse;

public interface MeetingService {
	Optional<CreateMeetingResponse> createMeeting(CreateMeetingRequest createMeetingRequest);
	Optional<MeetingsList> getMeetings(ListMeetingsRequest request);
	Optional<MeetingInviteResponse> getMeetingInvite(long meetingId);
	Optional<Boolean> deleteMeeting(long meetingId);
}
