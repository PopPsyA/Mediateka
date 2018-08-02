package com.ru.devit.mediateka.data;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationManagerCompat;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.DateAndTimeInfo;

import java.util.Calendar;
import java.util.Date;

public class CinemaNotificationReceiver extends BroadcastReceiver {

    public static final String CINEMA_NOTIFICATION_ACTION = "com.david.mediateka.cinema.notification.action";
    public static final String CINEMA_NOTIFICATION_DATE = appendCinemaNotificationPrefix("date");
    public static final String CINEMA_NOTIFICATION_ID = appendCinemaNotificationPrefix("id");
    public static final String CINEMA_NOTIFICATION_TITLE = appendCinemaNotificationPrefix("title");
    public static final String CINEMA_NOTIFICATION_CONTENT = appendCinemaNotificationPrefix("content");

    private static final String CINEMA_NOTIFICATION_ACTION_CREATE = "com.david.mediateka.cinema.notification.action.create";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (CINEMA_NOTIFICATION_ACTION.equals(action)){
            DateAndTimeInfo dateAndTimeInfo = (DateAndTimeInfo) intent.getSerializableExtra(CINEMA_NOTIFICATION_DATE);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, createScheduledIntent(intent , dateAndTimeInfo) , PendingIntent.FLAG_UPDATE_CURRENT);

            if (alarmManager != null) {
                long specificTimeInMillis = getFutureTimeInMillisFromDateAndTimeInfo(dateAndTimeInfo);
                if (specificTimeInMillis > currentTimeInMillis()){
                    long offsetBetweenSpecificTimeAndCurrentInMillis = SystemClock.elapsedRealtime() + (specificTimeInMillis - currentTimeInMillis());
                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP , offsetBetweenSpecificTimeAndCurrentInMillis, pendingIntent);
                }
            }

        } else if (CINEMA_NOTIFICATION_ACTION_CREATE.equals(action)){
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            Notification.Builder notificationBuilder = new Notification.Builder(context);
            notificationBuilder
                    .setContentTitle(getCinemaTitle(intent))
                    .setContentText(getCinemaDesc(intent))
                    .setTicker("Mediateka:" + getCinemaTitle(intent))
                    .setSmallIcon(R.drawable.ic_cinema);

            notificationManager.notify(getCinemaId(intent), notificationBuilder.build());
        }
    }

    private long getFutureTimeInMillisFromDateAndTimeInfo(DateAndTimeInfo dateAndTimeInfo){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.YEAR , dateAndTimeInfo.getYear());
        calendar.set(Calendar.MONTH , dateAndTimeInfo.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH , dateAndTimeInfo.getDay());
        calendar.set(Calendar.HOUR_OF_DAY , dateAndTimeInfo.getHour());
        calendar.set(Calendar.MINUTE , dateAndTimeInfo.getMinute());

        return calendar.getTimeInMillis();
    }

    private long currentTimeInMillis(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTimeInMillis();
    }

    private Intent createScheduledIntent(Intent intent , DateAndTimeInfo dateAndTimeInfo){
        String cinemaTitle = getCinemaTitle(intent);
        String cinemaDesc = getCinemaDesc(intent);
        int cinemaId = getCinemaId(intent);

        Intent scheduledCinemaIntent = new Intent(CINEMA_NOTIFICATION_ACTION_CREATE);
        scheduledCinemaIntent.putExtra(CINEMA_NOTIFICATION_TITLE , cinemaTitle);
        scheduledCinemaIntent.putExtra(CINEMA_NOTIFICATION_CONTENT , cinemaDesc);
        scheduledCinemaIntent.putExtra(CINEMA_NOTIFICATION_DATE , dateAndTimeInfo);
        scheduledCinemaIntent.putExtra(CINEMA_NOTIFICATION_ID , cinemaId);

        return scheduledCinemaIntent;
    }

    private int getCinemaId(Intent intent) {
        return intent.getIntExtra(CINEMA_NOTIFICATION_ID , 0);
    }

    private String getCinemaDesc(Intent intent) {
        return intent.getStringExtra(CINEMA_NOTIFICATION_CONTENT);
    }

    private String getCinemaTitle(Intent intent) {
        return intent.getStringExtra(CINEMA_NOTIFICATION_TITLE);
    }

    private static String appendCinemaNotificationPrefix(String str){
        return "cinema_notification_" + str;
    }
}
