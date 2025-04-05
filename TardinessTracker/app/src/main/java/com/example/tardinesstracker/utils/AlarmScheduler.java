package com.example.tardinesstracker.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.tardinesstracker.ui.student.AlarmReceiver;

import java.util.Calendar;

public class AlarmScheduler {
    
    /**
     * Schedule a smart alarm based on student's settings
     * @param context Application context
     * @param wakeUpTimeHour Hour to wake up (24-hour format)
     * @param wakeUpTimeMinute Minute to wake up
     * @param studentId Student ID for the alarm
     */
    public static void scheduleAlarm(Context context, int wakeUpTimeHour, int wakeUpTimeMinute, int studentId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("STUDENT_ID", studentId);
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                studentId, // Request code unique to this student
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        
        // Set time for alarm
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, wakeUpTimeHour);
        calendar.set(Calendar.MINUTE, wakeUpTimeMinute);
        calendar.set(Calendar.SECOND, 0);
        
        // If time is already past, set for next day
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Schedule the alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        } else {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        }
    }
    
    /**
     * Cancel a previously scheduled alarm for a student
     * @param context Application context
     * @param studentId Student ID for the alarm to cancel
     */
    public static void cancelAlarm(Context context, int studentId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                studentId,
                intent,
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
        );
        
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }
}
