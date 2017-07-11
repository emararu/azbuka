package ru.toolstrek.core;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Класс-приемник сообщений
 *
 * Created by EMararu on 03.07.2017.
 */
public class AbcBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = "Abc:AbcBroadcastReceiver";

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive:"+intent.getAction());

        //
        this.context = context;
        if (isRunning(this.context, AbcService.class)) {
            Log.i(TAG, "alredy running no need to start again");
        } else {
            // Создание службы
            Intent serviceIntent = new Intent(context, AbcService.class);
            // Старт службы
            context.startService(serviceIntent);
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
