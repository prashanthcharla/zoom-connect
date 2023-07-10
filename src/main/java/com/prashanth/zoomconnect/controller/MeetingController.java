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

import com.prashanth.zoomconnect.model.MeetingsList;
import com.prashanth.zoomconnect.model.request.CreateMeetingRequest;
import com.prashanth.zoomconnect.model.request.MeetingsListRequest;
import com.prashanth.zoomconnect.model.response.CreateMeetingResponse;
import com.prashanth.zoomconnect.model.response.MeetingInviteResponse;
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

	@GetMapping("/list")
	public ResponseEntity<MeetingsList> listMeetings(@RequestBody MeetingsListRequest request) {
		Optional<MeetingsList> res = meetingService.getMeetings(request);
		if (res.isPresent()) {
			return new ResponseEntity<MeetingsList>(res.get(), HttpStatus.OK);
		}

		return new ResponseEntity<MeetingsList>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/get-invite/{meetingId}")
	public ResponseEntity<MeetingInviteResponse> getMeetingInvitation(@PathVariable("meetingId") String meetingId) {
		Optional<MeetingInviteResponse> res = meetingService.getMeetingInvite(Long.parseLong(meetingId));
		if (res != null) {
			return new ResponseEntity<MeetingInviteResponse>(res.get(), HttpStatus.OK);
		}

		return new ResponseEntity<MeetingInviteResponse>(HttpStatus.BAD_REQUEST);
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
