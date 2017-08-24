package ru.toolstrek.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Класс-приемник сообщений
 *
 * Created by EMararu on 23.08.2017.
 */
public class AbcRequestReceiver extends BroadcastReceiver {

    private final String TAG = "Abc:AbcRequestReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive:"+intent.getAction());

        // Создание службы
        Intent serviceIntent = new Intent(context, AbcRequestService.class);
        // Старт службы
        context.startService(serviceIntent);
    }

}
