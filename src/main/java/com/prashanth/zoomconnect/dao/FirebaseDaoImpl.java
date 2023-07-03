package com.prashanth.zoomconnect.dao;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.prashanth.zoomconnect.model.OauthTokenInfo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FirebaseDaoImpl implements FirebaseDao {

	@Override
	public Optional<OauthTokenInfo> getOauthTokenInfoFromDocument(String collection, String document) {
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			DocumentReference docReference = firestore.collection(collection).document(document);
			ApiFuture<DocumentSnapshot> collectionApiFuture = docReference.get();
			DocumentSnapshot documentSnapshot = collectionApiFuture.get();
			if (documentSnapshot.exists())
				return Optional.of(documentSnapshot.toObject(OauthTokenInfo.class));
		} catch (Exception e) {
			log.error("Unable to fetch oauth token info from {}.{} {}", collection, document);
			e.printStackTrace();
		}

		return Optional.empty();
	}

	@Override
	public Optional<String> saveOauthTokenInfoToDocument(OauthTokenInfo oauthTokenInfo, String collection,
			String document) {
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			ApiFuture<WriteResult> collectionApiFuture = firestore.collection(collection).document(document)
					.set(oauthTokenInfo);
			return Optional.of(collectionApiFuture.get().getUpdateTime().toString());
		} catch (Exception e) {
			log.error("Unable to save oauth token info to {}.{} {}", collection, document);
			e.printStackTrace();
		}
		return Optional.empty();
	}

}
