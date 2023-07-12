import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { ZoomMtg } from '@zoomus/websdk';
import ZoomMtgEmbedded from "@zoomus/websdk/embedded";
import { BehaviorSubject, Observable, Subscription, catchError, throwError } from 'rxjs';
import { environment } from 'src/environment';
import { MeetingRequest } from '../model/request/Meeting.request.data';
import { MeetingsListRequest } from '../model/request/MeetingsList.request.data';
import { AuthResponse } from '../model/response/AuthCredResponse.response.data';
import { CreatedMeetingResponse } from '../model/response/CreatedMeeting.response.data';
import { MeetingsListResponse } from '../model/response/MeetingsList.response.data';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class MeetingService implements OnDestroy {

  isAnyMeetingInProgress$: Observable<boolean>;

  private _isAnyMeetingInProgress: BehaviorSubject<boolean>;
  private _requestUrl: string;
  private _redirectUrlOnLeave: string;
  private _subscriptions: Subscription;

  constructor(
    private _httpClient: HttpClient,
    private _authService: AuthService
  ) {
    this._isAnyMeetingInProgress = new BehaviorSubject<boolean>(false);
    this.isAnyMeetingInProgress$ = this._isAnyMeetingInProgress.asObservable();
    this._requestUrl = environment.apiUrl + "/api/meeting";
    this._redirectUrlOnLeave = "http://localhost:4200";
    this._subscriptions = new Subscription();
  }

  ngOnDestroy(): void {
    this._subscriptions.unsubscribe();
  }

  scheduleMeeting(request: MeetingRequest): Observable<CreatedMeetingResponse> {
    return this._httpClient.post<CreatedMeetingResponse>(this._requestUrl + "/create", request).pipe(catchError(this._handleError));
  }

  listMeetings(request: MeetingsListRequest): Observable<MeetingsListResponse> {
    return this._httpClient.post<MeetingsListResponse>(this._requestUrl + "/list", request).pipe(catchError(this._handleError));
  }

  getMeetingInvite(meetingId: number) {
    return this._httpClient.get(this._requestUrl + "/get-invite/" + meetingId, { responseType: "text" }).pipe(catchError(this._handleError));
  }

  getCreatedMeeting(meetingId: number): Observable<CreatedMeetingResponse> {
    return this._httpClient.get<CreatedMeetingResponse>(this._requestUrl + "/get-created-meeting/" + meetingId).pipe(catchError(this._handleError));
  }

  deleteMeeting(meetingId: number): Observable<Boolean> {
    return this._httpClient.delete<Boolean>(this._requestUrl + "/delete/" + meetingId).pipe(catchError(this._handleError));
  }

  startMeeting(meetingDetails: CreatedMeetingResponse, view: "client" | "component"): void {
    this._isAnyMeetingInProgress.next(true);

    this._subscriptions.add(
      this._authService.authCreds$.subscribe(cred => {
        let signatureRequest = {
          sdkKey: atob(cred.encryptedApiKey),
          sdkSecret: atob(cred.encryptedApiSecret),
          meetingNumber: meetingDetails.id.toString(),
          role: String(0),
          success: (success: any) => console.log(success),
          error: (error: any) => console.log(error),
        };
        let signature = ZoomMtg.generateSDKSignature(signatureRequest);
        if(view === "component")
          this._startMeetingInComponentView(meetingDetails.id, meetingDetails.password, cred, signature);
        else
          this._startMeetingInClientView(meetingDetails.id, meetingDetails.password, cred, signature);
      })
    );

  }

  private _startMeetingInComponentView(meetingId: number, meetingPassword: string, authCred: AuthResponse, signature: string) {
    const client = ZoomMtgEmbedded.createClient();
    let meetingSDKElement = document.getElementById('meetingSDKElement') as HTMLElement;
    client.init({ zoomAppRoot: meetingSDKElement, language: 'en-US' });
    client.join({
      sdkKey: atob(authCred.encryptedApiKey),
      signature: signature,
      meetingNumber: meetingId.toString(),
      password: meetingPassword,
      userName: atob(authCred.encryptedUserName),
      success: (success: any) => {
        console.log(success);
        this._listenForLeaveEvent();
      },
      error: (error: any) => {
        console.error(error);
        this._isAnyMeetingInProgress.next(false);
      }
    });
  }

  private _startMeetingInClientView(meetingId: number, meetingPassword: string, authCred: AuthResponse, signature: string) {
    ZoomMtg.preLoadWasm();
    ZoomMtg.prepareWebSDK();

    // loads language files, also passes any error messages to the ui
    ZoomMtg.i18n.load('en-US');
    ZoomMtg.i18n.reload('en-US');

    ZoomMtg.setZoomJSLib('https://source.zoom.us/2.14.0/lib', '/av');
    (document.getElementById('zmmtg-root') as HTMLElement).style.display = 'block';
    ZoomMtg.init({
      leaveUrl: this._redirectUrlOnLeave,
      isSupportAV: true,
      isSupportBreakout: true,
      disableInvite: false,
      success: (success: any) => {
        console.log(success);
        this._joinMeeting(authCred, signature, meetingId, meetingPassword);
      },
      error: (error: any) => {
        console.error(error);
        this._isAnyMeetingInProgress.next(false);
      }
    });
  }

  private _joinMeeting(authCred: AuthResponse, signature: string, meetingId: number, meetingPassword: string): void {
    ZoomMtg.join({
      signature: signature.toString(),
      meetingNumber: meetingId,
      userName: atob(authCred.encryptedUserName),
      userEmail: atob(authCred.encryptedUserId),
      sdkKey: atob(authCred.encryptedApiKey),
      passWord: meetingPassword,
      success: (success: any) => {
        console.log(success);
        this._listenForLeaveEvent();
      },
      error: (error: any) => console.error(error)
    })
  }

  private _listenForLeaveEvent(): void {
    // Register for the 'meeting.status' event to be notified when the meeting ends
    ZoomMtg.inMeetingServiceListener('onMeetingStatus', (data: any) => {
      if (data.status === 'MEETING_ENDED') {
        this._isAnyMeetingInProgress.next(false);
      }
    });
  }

  private _handleError(error: any) {
    return throwError(error.message || "Server Error");
  }
}
