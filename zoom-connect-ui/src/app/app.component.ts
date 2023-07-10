import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {

  private _subscriptions: Subscription;

  constructor(private _authService: AuthService) {
    this._subscriptions = new Subscription();
  }

  ngOnInit(): void {
    this._authService.getAuthCreds();
  }
}
