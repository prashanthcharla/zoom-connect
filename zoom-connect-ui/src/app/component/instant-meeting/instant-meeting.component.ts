import { LiveAnnouncer } from '@angular/cdk/a11y';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, inject } from '@angular/core';
import { MatChipEditedEvent, MatChipInputEvent } from '@angular/material/chips';

@Component({
  selector: 'app-instant-meeting',
  templateUrl: './instant-meeting.component.html',
  styleUrls: ['./instant-meeting.component.scss']
})
export class InstantMeetingComponent {

  separatorKeysCodes: number[];
  selectedParticipants: Array<string>;
  announcer: LiveAnnouncer;
  enableControls: boolean;
  enableSpinner: boolean;

  constructor() {
    this.separatorKeysCodes = [ENTER, COMMA];
    this.selectedParticipants = new Array<string>();
    this.announcer = inject(LiveAnnouncer);
    this.enableControls = true;
    this.enableSpinner = false;
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
    this.enableControls = false;
    this.enableSpinner = true;
  }

}
