import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SignatureRequest } from '../model/request/Signature.request.data';
import { environment } from 'src/environment';

@Injectable({
  providedIn: 'root'
})
export class SignatureService {
  private _requestUrl: string;

  constructor(private _httpClient: HttpClient) {
    this._requestUrl = environment.apiUrl + "/api/signature";
  }

  generateSignature(request: SignatureRequest): Observable<string> {
    return this._httpClient.post(this._requestUrl + "/generate", request, { responseType: 'text' });
  }
}
