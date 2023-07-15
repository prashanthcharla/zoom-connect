import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Subscription, combineLatest } from 'rxjs';
import { MeetingsListRequest } from 'src/app/model/request/MeetingsList.request.data';
import { MeetingsListResponse } from 'src/app/model/response/MeetingsList.response.data';
import { AuthService } from 'src/app/service/auth.service';
import { MeetingService } from 'src/app/service/meeting.service';

export type MeetingType = "scheduled" | "upcoming" | "live";

@Component({
  selector: 'app-my-meetings',
  templateUrl: './my-meetings.component.html',
  styleUrls: ['./my-meetings.component.scss']
})
export class MyMeetingsComponent implements OnInit, OnDestroy {

  selectedType: FormControl<MeetingType | null>;
  meetingsList: MeetingsListResponse;
  showInfoForMeet: Array<boolean>;
  enableSpinner: boolean;

  private _subscriptions: Subscription;

  constructor(
    private _authService: AuthService,
    private _meetingService: MeetingService
  ) {
    this.selectedType = new FormControl<MeetingType | null>(null);
    this.meetingsList = {} as MeetingsListResponse;
    this.showInfoForMeet = new Array<boolean>();
    this.enableSpinner = true;
    this._subscriptions = new Subscription();
  }

  ngOnInit(): void {
    this._subscriptions.add(
      combineLatest(
        [
          this._authService.authCreds$,
          this.selectedType.valueChanges
        ],
        (cred, type) => {
          if (type && cred?.encryptedApiKey) {
            this.enableSpinner = true;
            let meetingsRequest = {
              userId: atob(cred.encryptedUserId),
              type
            } as MeetingsListRequest;
            this._meetingService.listMeetings(meetingsRequest).subscribe(meetings => {
              this.meetingsList = meetings;
              this.showInfoForMeet = new Array<boolean>(meetings.meetings.length);
              this.showInfoForMeet.fill(false);
              this.enableSpinner = false;
            });
          }
        }
      ).subscribe()
    );

    this.selectedType.setValue("scheduled", { emitEvent: true });
  }

  ngOnDestroy(): void {
    this._subscriptions.unsubscribe();
  }

}
