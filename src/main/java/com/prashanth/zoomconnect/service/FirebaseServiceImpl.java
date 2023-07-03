package com.prashanth.zoomconnect.service;

import java.io.FileInputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.prashanth.zoomconnect.dao.FirebaseDao;
import com.prashanth.zoomconnect.model.OauthTokenInfo;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {

	@Autowired
	private FirebaseDao firebaseDao;

	@PostConstruct
	public void initialize() {
		try {
			log.info("Start: Firebase app initialization.");
			FileInputStream serviceAccount = new FileInputStream(
					ResourceUtils.getFile("classpath:firebase/ServiceAccountKey.json"));

			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}
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

	@Override
	public void refreshOauthToken() {
		// TODO Auto-generated method stub

	}
}
