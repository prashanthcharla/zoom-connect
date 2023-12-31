import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InstantMeetingComponent } from './component/instant-meeting/instant-meeting.component';
import { MyMeetingsComponent } from './component/my-meetings/my-meetings.component';

const routes: Routes = [
  { path: '', redirectTo: 'instant-meeting', pathMatch: 'full' },
  { path: 'instant-meeting', component: InstantMeetingComponent },
  { path: 'list', component: MyMeetingsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
