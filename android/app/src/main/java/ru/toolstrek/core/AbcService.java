package ru.toolstrek.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Загрузка приложения как сервиса
 *
 * Created by EMararu on 03.07.2017.
 */
public class AbcService extends Service {

    private final String TAG = "Abc:AbcService";

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // something to do when the service is created
        Log.i(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand");

        //if ((flags & START_FLAG_RETRY) == 0) {
            // TODO Если это повторный запуск, выполнить какие-то действия.
        //}
        //else {
            // TODO Альтернативные действия в фоновом режиме.
        //}

        /*String alarm = Context.ALARM_SERVICE;
        AlarmManager alarmManager = (AlarmManager)getSystemService(alarm);

        Intent notificationIntent = new Intent("ru.toolstrek.action.restart");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 231181, notificationIntent, 0);

        int type = AlarmManager.RTC_WAKEUP;
        long interval = 1000 * 50;
        alarmManager.setInexactRepeating(type, System.currentTimeMillis(), interval, pendingIntent);*/

        return Service.START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(TAG, "onTaskRemoved:"+rootIntent.getAction());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        Intent broadcastIntent = new Intent("ru.toolstrek.action.restart");
        sendBroadcast(broadcastIntent);
    }
}
