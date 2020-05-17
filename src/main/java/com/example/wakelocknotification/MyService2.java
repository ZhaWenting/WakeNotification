package com.example.wakelocknotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author adazha
 * @Date 03/07/2019
 * @Desp
 */
public class MyService2 extends Service {


    private static final long INTERVAL = 3 * 1000;
    private Handler handler = new Handler();
    private Timer mTimer;
    private PowerManager.WakeLock wakeLock;
    private PowerManager powerManager;
    NotificationManager mNotifyMgr;
    private NotificationUtil util;
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, MyService.class.getName());
        wakeLock.acquire();

        // 如果已经存在，则先取消
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }
        mTimer.scheduleAtFixedRate(new MyTimerTask(), 3000, INTERVAL);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            handler.post(runnable);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(!powerManager.isScreenOn()) {
                wakeLock = powerManager.newWakeLock(
                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
                        MyService.class.getName());
                wakeLock.acquire();
                wakeLock.release();
                util = new NotificationUtil(getBaseContext());
                util.sendNotification("title","content");
            }

            Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
        }

    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null)
            mTimer.cancel();
        if (wakeLock != null) {
            wakeLock.release();
            wakeLock = null;
        }
    }


}
