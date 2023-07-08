package com.prashanth.zoomconnect.dao;

import java.util.Optional;

import com.prashanth.zoomconnect.model.CreateMeetingResponse;
import com.prashanth.zoomconnect.model.OauthTokenInfo;

public interface FirebaseDao {
	public Optional<OauthTokenInfo> getOauthTokenInfoFromDocument(String collection, String document);

	public Optional<String> saveOauthTokenInfoToDocument(OauthTokenInfo oauthTokenInfo, String collection,
			String document);

	public Optional<String> saveCreatedMeeting(CreateMeetingResponse createMeetingResponse, String collection, String document);

	public Optional<CreateMeetingResponse> getCreatedMeeting(String collection, String document);
}
