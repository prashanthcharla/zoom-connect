import { LiveAnnouncer } from '@angular/cdk/a11y';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, OnInit, inject } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatChipEditedEvent, MatChipInputEvent } from '@angular/material/chips';
import { Subscription } from 'rxjs';
import { MeetingRequest } from 'src/app/model/request/Meeting.request.data';
import { MeetingService } from 'src/app/service/meeting.service';

@Component({
  selector: 'app-instant-meeting',
  templateUrl: './instant-meeting.component.html',
  styleUrls: ['./instant-meeting.component.scss']
})
export class InstantMeetingComponent implements OnInit {

  separatorKeysCodes: number[];
  selectedParticipants: Array<string>;
  topicControl: FormControl;
  announcer: LiveAnnouncer;
  enableControls: boolean;
  enableSpinner: boolean;

  private _subscriptions: Subscription;

  constructor(
    private _meetingService: MeetingService
  ) {
    this.separatorKeysCodes = [ENTER, COMMA];
    this.selectedParticipants = new Array<string>();
    this.topicControl = new FormControl();
    this.announcer = inject(LiveAnnouncer);
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

  addParticipant(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      this.selectedParticipants.push(value);
    }
    event.chipInput!.clear();
  }

  editParticipant(participant: string, event: MatChipEditedEvent) {
    const value = event.value.trim();

    // Remove participent if it no longer has a value
    if (!value) {
      this.removeParticipant(participant);
      return;
    }

    // Edit existing participent
    const index = this.selectedParticipants.indexOf(participant);
    if (index >= 0) {
      this.selectedParticipants[index] = value;
    }
  }

  removeParticipant(participant: string): void {
    const index = this.selectedParticipants.indexOf(participant);

    if (index >= 0) {
      this.selectedParticipants.splice(index, 1);

      this.announcer.announce(`Removed ${participant}`);
    }
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
