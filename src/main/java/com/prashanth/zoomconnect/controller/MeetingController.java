package com.prashanth.zoomconnect.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashanth.zoomconnect.model.CreateMeetingRequest;
import com.prashanth.zoomconnect.model.CreateMeetingResponse;
import com.prashanth.zoomconnect.service.FirebaseService;
import com.prashanth.zoomconnect.service.MeetingService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/meeting")
public class MeetingController {

	@Autowired
	MeetingService meetingService;

	@Autowired
	FirebaseService firebaseService;

	@PostMapping("/create")
	public ResponseEntity<CreateMeetingResponse> createMeeting(@RequestBody CreateMeetingRequest createMeetingReq) {
		Optional<CreateMeetingResponse> res = meetingService.createMeeting(createMeetingReq);
		if (res.isPresent()) {
			return new ResponseEntity<CreateMeetingResponse>(res.get(), HttpStatus.OK);
		}

		return new ResponseEntity<CreateMeetingResponse>(HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/delete/{meetingId}")
	public ResponseEntity<Boolean> deleteMeeting(@PathVariable("meetingId") long meetingId) {
		Optional<Boolean> res = meetingService.deleteMeeting(meetingId);
		if (res.isPresent()) {
			return new ResponseEntity<Boolean>(res.get(), HttpStatus.OK);
		}

		return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/list/{userId}/{type}")
	public ResponseEntity<String> listMeetings(@PathVariable("userId") String userId,
			@PathVariable("type") String type) {
		Optional<String> res = meetingService.getMeetings(userId, type);
		if (res != null) {
			return new ResponseEntity<String>(res.get(), HttpStatus.OK);
		}

		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/get-invite/{meetingId}")
	public ResponseEntity<String> getMeetingInvitation(@PathVariable("meetingId") String meetingId) {
		Optional<String> res = meetingService.getMeetingInvite(Long.parseLong(meetingId));
		if (res != null) {
			return new ResponseEntity<String>(res.get(), HttpStatus.OK);
		}

		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/get-created-meeting/{meetingId}")
	public ResponseEntity<CreateMeetingResponse> getCreatedMeeting(@PathVariable("meetingId") String meetingId) {
		Optional<CreateMeetingResponse> res = firebaseService.getCreatedMeeting(meetingId);
		if (res.isPresent()) {
			return new ResponseEntity<CreateMeetingResponse>(res.get(), HttpStatus.OK);
		}

		return new ResponseEntity<CreateMeetingResponse>(HttpStatus.BAD_REQUEST);
	}

}
