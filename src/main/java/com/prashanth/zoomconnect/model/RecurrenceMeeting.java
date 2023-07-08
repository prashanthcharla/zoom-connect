package com.prashanth.zoomconnect.model;

import lombok.Data;

@Data
public class RecurrenceMeeting {
	private int type;
    private int repeat_interval;
    private String weekly_days;
    private int monthly_day;
    private int monthly_week;
    private int monthly_week_day;
    private int end_times;
    private String end_date_time;
}
