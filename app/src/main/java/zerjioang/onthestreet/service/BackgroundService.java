package zerjioang.onthestreet.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.model.pojox.Place;

/**
 * Created by .local on 22/04/2017.
 */

public class BackgroundService extends Service{

    private static final long DEFAULT_TASK_DELAY = 0;   //ms
    private static final long TASK_PERIOD = 60 * 1000;  //ms

    private Place nearestPlace;
    private Timer mTimer;
    private TimerTask timerTask;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTimer = new Timer();
        //initialize our timer
        timerTask = new TimerTask() {
            public void run() {
                checkAndSendNotificationIfNeeded();
            }
        };
        mTimer.schedule(timerTask, DEFAULT_TASK_DELAY, TASK_PERIOD);
    }

    private void checkAndSendNotificationIfNeeded() {

    }

    private void showNotification(String title, String msg){
        //https://developer.android.com/training/notify-user/build-notification.html#action
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(msg);
        mBuilder.build();
    }

    @Override
    public void onDestroy() {
        try {
            mTimer.cancel();
            timerTask.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        sendBroadcast(intent);
    }

}