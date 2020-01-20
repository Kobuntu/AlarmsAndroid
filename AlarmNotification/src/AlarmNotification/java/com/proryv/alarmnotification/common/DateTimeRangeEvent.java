package com.proryv.alarmnotification.common;

import java.util.Calendar;

/**
 * Created by Ig on 12.07.13.
 */
public interface DateTimeRangeEvent {
    void OnRangeSelected(Calendar dtStart, Calendar dtEnd, Boolean isAlarmDateTimeChanged);
}
