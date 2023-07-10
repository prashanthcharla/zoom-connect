import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScheduleAMeetingComponent } from './schedule-a-meeting.component';

describe('ScheduleAMeetingComponent', () => {
  let component: ScheduleAMeetingComponent;
  let fixture: ComponentFixture<ScheduleAMeetingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScheduleAMeetingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScheduleAMeetingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
