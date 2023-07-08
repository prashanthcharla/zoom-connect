package com.prashanth.zoomconnect.service;

import java.util.Optional;

import com.prashanth.zoomconnect.model.CreateMeetingResponse;
import com.prashanth.zoomconnect.model.OauthTokenInfo;

public interface FirebaseService {
	public Optional<OauthTokenInfo> getOauthToken(String collection, String document);

	public Optional<String> saveOauthToken(OauthTokenInfo oauthTokenInfo, String collection, String document);

	public Optional<String> saveCreatedMeeting(CreateMeetingResponse createMeetingResponse);

	public Optional<CreateMeetingResponse> getCreatedMeeting(String meetingId);
}
