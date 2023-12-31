package com.prashanth.zoomconnect.service;

import java.io.FileInputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.prashanth.zoomconnect.config.ZoomMetadata;
import com.prashanth.zoomconnect.dao.FirebaseDao;
import com.prashanth.zoomconnect.model.OauthTokenInfo;
import com.prashanth.zoomconnect.model.response.CreateMeetingResponse;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {

	public static boolean oauthTokenRefreshIsInProgress;

	@Autowired
	private FirebaseDao firebaseDao;

	@Autowired
	private ZoomMetadata zoomMetadata;

	@PostConstruct
	public void initialize() {
		oauthTokenRefreshIsInProgress = false;
		try {
			log.info("Start: Firebase app initialization.");
			FileInputStream serviceAccount = new FileInputStream(
					ResourceUtils.getFile("classpath:firebase/ServiceAccountKey.json"));

			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

			if (FirebaseApp.getApps().isEmpty()) {
				log.info("Initializing Firebase");
				FirebaseApp.initializeApp(options);
			} else {
				log.info("Already exists!");
			}

			this.refreshOauthToken();
			log.info("End: Firebase app initialization.");
		} catch (Exception e) {
			log.error("Failure: Firebase app initialization.");
			e.printStackTrace();
		}
	}

	@Override
	public Optional<OauthTokenInfo> getOauthToken(String collection, String document) {
		return firebaseDao.getOauthTokenInfoFromDocument(collection, document);
	}

	@Override
	public Optional<String> saveOauthToken(OauthTokenInfo oauthTokenInfo, String collection, String document) {
		return firebaseDao.saveOauthTokenInfoToDocument(oauthTokenInfo, collection, document);
	}

	@Scheduled(fixedRate = 3300000)
	public void refreshOauthToken() {
		log.info("Start: Refresh access token.");
		oauthTokenRefreshIsInProgress = true;
		String url = "https://api.zoom.us/oauth/token";
		Optional<OauthTokenInfo> existingOauthTokenInfo = this.getOauthToken(
				zoomMetadata.getOauthTokenStorageCollection(), zoomMetadata.getOauthTokenStorageDocument());
		if (existingOauthTokenInfo.isPresent()) {
			// Set headers
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			// Set request parameters
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("grant_type", "refresh_token");
			map.add("refresh_token", existingOauthTokenInfo.get().getRefresh_token());
			map.add("client_id", zoomMetadata.getClientId());
			map.add("client_secret", zoomMetadata.getClientSecret());

			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

			// Call zoom API to refresh the token
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<OauthTokenInfo> response = restTemplate.exchange(url, HttpMethod.POST, entity,
					OauthTokenInfo.class);

			// Replace the active OAuth token with new token
			OauthTokenInfo refreshedOauthTokenInfo = response.getBody();
			Optional<String> savedTimestamp = this.saveOauthToken(refreshedOauthTokenInfo,
					zoomMetadata.getOauthTokenStorageCollection(), zoomMetadata.getOauthTokenStorageDocument());

			if (savedTimestamp.isPresent())
				log.info("Complete: Refresh access token");
		} else {
			log.error("Existing aouth token info is empty. Thereby, unable to refresh access token.");
		}

		oauthTokenRefreshIsInProgress = false;
	}

	@Override
	public Optional<String> saveCreatedMeeting(CreateMeetingResponse createMeetingResponse) {
		return firebaseDao.saveCreatedMeeting(createMeetingResponse, zoomMetadata.getCreatedMeetingsStorageCollection(),
				String.valueOf(createMeetingResponse.getId()));
	}

	@Override
	public Optional<CreateMeetingResponse> getCreatedMeeting(String meetingId) {
		return firebaseDao.getCreatedMeeting(zoomMetadata.getCreatedMeetingsStorageCollection(), meetingId);
	}
}
