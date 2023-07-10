import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { environment } from 'src/environment';
import { AuthResponse } from '../model/response/AuthCredResponse.response.data';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnDestroy {
  authCreds$: Observable<AuthResponse>;

  private _requestUrl: string;
  private _authCreds: BehaviorSubject<AuthResponse>;
  private _subscriptions: Subscription;

  constructor(
    private _httpClient: HttpClient
  ) {
    this._requestUrl = environment.apiUrl + "/api/auth";
    this._authCreds = new BehaviorSubject<AuthResponse>({} as AuthResponse);
    this.authCreds$ = this._authCreds.asObservable();
    this._subscriptions = new Subscription();
  }

  ngOnDestroy(): void {
    this._authCreds.complete();
    this._subscriptions.unsubscribe();
  }

  getAuthCreds(): void {
    this._subscriptions.add(
      this._httpClient.get<AuthResponse>(this._requestUrl + "/getAuthCred").subscribe(cred => this._authCreds.next(cred))
    );
  }
}
