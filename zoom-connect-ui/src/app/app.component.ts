import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { AuthService } from './service/auth.service';
import { MeetingService } from './service/meeting.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {

  isAnyMeetingRunning$: Observable<boolean>;

  constructor(
    private _authService: AuthService,
    private _meetingService: MeetingService
  ) {
    this.isAnyMeetingRunning$ = of(false);
  }

  ngOnInit(): void {
    this._authService.getAuthCreds();
    this.isAnyMeetingRunning$ = this._meetingService.isAnyMeetingInProgress$;
  }
}
