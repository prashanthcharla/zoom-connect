import { LiveAnnouncer } from '@angular/cdk/a11y';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { MatChipEditedEvent, MatChipInputEvent } from '@angular/material/chips';

@Component({
  selector: 'app-participant',
  templateUrl: './participant.component.html',
  styleUrls: ['./participant.component.scss']
})
export class ParticipantComponent {

  @Input() selectedParticipants: Array<string>;
  @Input() enableParticipantControl: boolean = true;
  @Output() participantsChangeEvent = new EventEmitter<Array<string>>();
  announcer: LiveAnnouncer;
  separatorKeysCodes: number[];

  constructor() {
    this.separatorKeysCodes = [ENTER, COMMA];
    this.selectedParticipants = new Array<string>();
    this.announcer = inject(LiveAnnouncer);
  }

  addParticipant(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      this.selectedParticipants.push(value);
    }
    event.chipInput!.clear();
    this.participantsChangeEvent.emit(this.selectedParticipants);
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

    this.participantsChangeEvent.emit(this.selectedParticipants);
  }

  removeParticipant(participant: string): void {
    const index = this.selectedParticipants.indexOf(participant);

    if (index >= 0) {
      this.selectedParticipants.splice(index, 1);

      this.announcer.announce(`Removed ${participant}`);
    }

    this.participantsChangeEvent.emit(this.selectedParticipants);
  }

}
