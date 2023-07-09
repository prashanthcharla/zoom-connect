import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SignatureRequest } from '../model/request/Signature.request.data';

@Injectable({
  providedIn: 'root'
})
export class SignatureService {
  private requestUrl: string;

  constructor(private httpClient: HttpClient) {
    this.requestUrl = "/api/signature";
  }

  generateSignature(request: SignatureRequest): Observable<String> {
    return this.httpClient.post(this.requestUrl + "/generate", request, { responseType: 'text' });
  }
}
