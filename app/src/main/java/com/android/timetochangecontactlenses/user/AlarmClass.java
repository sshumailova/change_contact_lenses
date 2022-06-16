package com.android.timetochangecontactlenses.user;

import android.app.AlarmManager;
import android.app.PendingIntent;

public class AlarmClass {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


//    public void cancelAlarm() {
//        Intent intent = new Intent(, AlarmReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//        if (alarmManager == null) {
//            alarmManager = (AlarmManager)  getSystemService(Context.ALARM_SERVICE);
//        }
//        alarmManager.cancel(pendingIntent);
//        Toast.makeText(this, "ALarm cancelled", Toast.LENGTH_LONG).show();
//
//    }
//    public void setAlarm(Calendar calendar) {
//        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, AlarmReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//        Log.d("MyLog", "GETTIME" + calendar.getTimeInMillis());
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//        Toast.makeText(this, "ALarm set Successfully", Toast.LENGTH_LONG).show();
//    }
}
