import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { ZoomMtg } from '@zoomus/websdk';
import { Observable, Subscription, catchError, combineLatest, throwError } from 'rxjs';
import { environment } from 'src/environment';
import { MeetingRequest } from '../model/request/Meeting.request.data';
import { MeetingsListRequest } from '../model/request/MeetingsList.request.data';
import { SignatureRequest } from '../model/request/Signature.request.data';
import { AuthResponse } from '../model/response/AuthCredResponse.response.data';
import { CreatedMeetingResponse } from '../model/response/CreatedMeeting.response.data';
import { MeetingsListResponse } from '../model/response/MeetingsList.response.data';
import { AuthService } from './auth.service';
import { SignatureService } from './signature.service';

@Injectable({
  providedIn: 'root'
})
export class MeetingService implements OnDestroy {

  private _requestUrl: string;
  private _redirectUrlOnLeave: string;
  private _subscriptions: Subscription;

  constructor(
    private _httpClient: HttpClient,
    private _authService: AuthService,
    private _signatureService: SignatureService
  ) {
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

  startMeeting(meetingId: number, meetingPassword: string): void {
    let signatureRequest = {
      meetingId: meetingId.toString(),
      role: 1
    } as SignatureRequest;
    this._subscriptions.add(
      combineLatest(
        this._authService.authCreds$,
        this._signatureService.generateSignature(signatureRequest)
      ).subscribe(([authCred, signature]) => {
        ZoomMtg.init({
          leaveUrl: this._redirectUrlOnLeave,
          isSupportAV: true,
          isSupportBreakout: true,
          disableInvite: false,
          success: () => this._joinMeeting(authCred, signature, meetingId, meetingPassword),
          error: () => console.error("Initialization failed.")
        })
      })
    );
    // document.getElementById('zmmtg-root').style.display = 'block'
  }

  private _joinMeeting(authCred: AuthResponse, signature: string, meetingId: number, meetingPassword: string): void {
    ZoomMtg.join({
      signature: signature.toString(),
      meetingNumber: meetingId,
      userName: atob(authCred.encryptedUserName),
      sdkKey: atob(authCred.encryptedApiKey),
      passWord: meetingPassword,
      success: () => console.log("Joined"),
      error: () => console.error("Failed to join")
    })
  }

  private _handleError(error: any) {
    return throwError(error.message || "Server Error");
  }
}
