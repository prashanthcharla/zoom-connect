import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserModule } from '@angular/platform-browser';

import { AngularFireDatabaseModule } from '@angular/fire//compat/database';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireAuthModule } from '@angular/fire/compat/auth';
import { AngularFireMessagingModule } from '@angular/fire/compat/messaging';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { InstantMeetingComponent } from './component/instant-meeting/instant-meeting.component';
import { FIREBASE_CONFIG } from './helper/Constants';
import { MaterialModule } from './material/material.module';
import { MyMeetingsComponent } from './component/my-meetings/my-meetings.component';
import { ScheduleAMeetingComponent } from './component/schedule-a-meeting/schedule-a-meeting.component';
import { ParticipantComponent } from './component/participant/participant.component';

@NgModule({
  declarations: [
    AppComponent, 
    InstantMeetingComponent,
    ScheduleAMeetingComponent,
    MyMeetingsComponent,
    ParticipantComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    AngularFireModule.initializeApp(FIREBASE_CONFIG),
    AngularFireDatabaseModule,
    AngularFireAuthModule,
    AngularFireMessagingModule,
    FlexLayoutModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
