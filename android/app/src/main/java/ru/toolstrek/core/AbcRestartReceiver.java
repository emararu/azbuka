package ru.toolstrek.core;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Класс-приемник сообщений
 *
 * Created by EMararu on 03.07.2017.
 */
public class AbcRestartReceiver extends BroadcastReceiver {

    private final String TAG = "Abc:AbcRestartReceiver";

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive:"+intent.getAction());

        // Если есть аутентифицированный пользователь для которого выполнять опрос
        //if (AbcUser.check == 1) {

        //}
        this.context = context;

        if (isRunning(this.context, AbcRequestService.class)) {
            Log.i(TAG, "alredy running no need to start again");
        } else {
            // Создание службы
            //Intent serviceIntent = new Intent(context, AbcRequestService.class);
            // Старт службы
            //context.startService(serviceIntent);

            Log.i(TAG, "Set alarm");

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            //Intent i = new Intent(context, AbcRequestReceiver.class);
            Intent i = new Intent("ru.toolstrek.action.request");

            // intent
            PendingIntent intentExecuted = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
            Calendar now = Calendar.getInstance();
            alarmManager.setInexactRepeating(AlarmManager.RTC, now.getTimeInMillis(), 10 * 1000, intentExecuted);
        }
    }

    private boolean isRunning(Context context, Class<?> serviceClass) {
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName()) && service.pid != 0) {
                return true;
            }
        }
        return false;
    }
}
