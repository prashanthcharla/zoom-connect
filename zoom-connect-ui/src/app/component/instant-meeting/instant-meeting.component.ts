import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Subscription } from 'rxjs';
import { MeetingRequest } from 'src/app/model/request/Meeting.request.data';
import { MeetingService } from 'src/app/service/meeting.service';

@Component({
  selector: 'app-instant-meeting',
  templateUrl: './instant-meeting.component.html',
  styleUrls: ['./instant-meeting.component.scss']
})
export class InstantMeetingComponent implements OnInit {

  selectedParticipants: Array<string>;
  topicControl: FormControl;
  enableControls: boolean;
  enableSpinner: boolean;

  private _subscriptions: Subscription;

  constructor(
    private _meetingService: MeetingService
  ) {
    this.selectedParticipants = new Array<string>();
    this.topicControl = new FormControl();
    this.enableControls = true;
    this.enableSpinner = false;
    this._subscriptions = new Subscription();
  }

  ngOnInit(): void {
    this._subscriptions.add(
      this._meetingService.isAnyMeetingInProgress$.subscribe(value => {
        if (!value) {
          this.enableControls = true;
          this.enableSpinner = false;
          this.topicControl.enable();
        } else {
          this.enableControls = false;
          this.enableSpinner = true;
          this.topicControl.disable();
        }
      })
    );
  }

  updateParticipants(values: Array<string>) {
    this.selectedParticipants = values;
  }

  startMeeting(): void {
    let meetingRequest = {
      topic: this.topicControl.value,
      type: 1
    } as MeetingRequest;

    this._subscriptions.add(
      this._meetingService.scheduleMeeting(meetingRequest).subscribe(response => {
        this._meetingService.startMeeting(response, "component");
      })
    );
  }
}
