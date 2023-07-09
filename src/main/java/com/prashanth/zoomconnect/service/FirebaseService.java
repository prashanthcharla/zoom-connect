package com.prashanth.zoomconnect.service;

import java.util.Optional;

import com.prashanth.zoomconnect.model.OauthTokenInfo;
import com.prashanth.zoomconnect.model.response.CreateMeetingResponse;

public interface FirebaseService {
	public Optional<OauthTokenInfo> getOauthToken(String collection, String document);

	public Optional<String> saveOauthToken(OauthTokenInfo oauthTokenInfo, String collection, String document);

	public Optional<String> saveCreatedMeeting(CreateMeetingResponse createMeetingResponse);

	public Optional<CreateMeetingResponse> getCreatedMeeting(String meetingId);
}
