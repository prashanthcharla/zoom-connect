package com.prashanth.zoomconnect.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.prashanth.zoomconnect.config.ZoomMetadata;
import com.prashanth.zoomconnect.model.CreateMeetingRequest;
import com.prashanth.zoomconnect.model.CreateMeetingResponse;
import com.prashanth.zoomconnect.model.OauthTokenInfo;

import jakarta.annotation.PostConstruct;

@Service
public class MeetingServiceImpl implements MeetingService {

	@Autowired
	private ZoomMetadata zoomMetadata;

	@Autowired
	private FirebaseService firebaseService;

	private RestTemplate restTemplate;

	private Optional<OauthTokenInfo> oauthTokenInfo;

	@PostConstruct
	private void initialize() {
		restTemplate = new RestTemplate();
		oauthTokenInfo = firebaseService.getOauthToken(zoomMetadata.getOauthTokenStorageCollection(),
				zoomMetadata.getOauthTokenStorageDocument());
	}

	@Override
	public Optional<CreateMeetingResponse> createMeeting(CreateMeetingRequest createMeetingRequest) {
		if (oauthTokenInfo.isPresent()) {
			String accessToken = oauthTokenInfo.get().getAccess_token();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "Bearer " + accessToken);
			HttpEntity<?> entity = new HttpEntity<Object>(createMeetingRequest, headers);
			while (FirebaseServiceImpl.oauthTokenRefreshIsInProgress) {
			}
			ResponseEntity<CreateMeetingResponse> response = restTemplate.postForEntity(
					"https://api.zoom.us/v2/users/prashanthcharla1234@gmail.com/meetings", entity,
					CreateMeetingResponse.class);
			firebaseService.saveCreatedMeeting(response.getBody());
			return Optional.of(response.getBody());
		}

		return Optional.empty();
	}

	@Override
	public Optional<String> getMeetings(String userId, String type) {
		if (oauthTokenInfo.isPresent()) {
			String accessToken = oauthTokenInfo.get().getAccess_token();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + accessToken);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			while (FirebaseServiceImpl.oauthTokenRefreshIsInProgress) {
			}
			ResponseEntity<String> response = restTemplate.exchange(
					"https://api.zoom.us/v2/users/" + userId + "/meetings?type=" + type, HttpMethod.GET, entity,
					String.class);
			return Optional.of(response.getBody());
		}

		return Optional.empty();
	}

	@Override
	public Optional<String> getMeetingInvite(long meetingId) {
		if (oauthTokenInfo.isPresent()) {
			String accessToken = oauthTokenInfo.get().getAccess_token();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + accessToken);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			while (FirebaseServiceImpl.oauthTokenRefreshIsInProgress) {
			}
			ResponseEntity<String> response = restTemplate.exchange(
					"https://api.zoom.us/v2/meetings/" + meetingId + "/invitation", HttpMethod.GET, entity,
					String.class);
			return Optional.of(response.getBody());
		}

		return Optional.empty();

	}

	@Override
	public Optional<Boolean> deleteMeeting(long meetingId) {
		if (oauthTokenInfo.isPresent()) {
			String accessToken = oauthTokenInfo.get().getAccess_token();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Bearer " + accessToken);
			HttpEntity<?> entity = new HttpEntity<>(headers);
			while (FirebaseServiceImpl.oauthTokenRefreshIsInProgress) {
			}
			ResponseEntity<String> response = restTemplate.exchange("https://api.zoom.us/v2//meetings/" + meetingId,
					HttpMethod.DELETE, entity, String.class);
			return Optional.of(response.getStatusCode().equals(HttpStatus.OK));
		}

		return Optional.empty();
	}

}
