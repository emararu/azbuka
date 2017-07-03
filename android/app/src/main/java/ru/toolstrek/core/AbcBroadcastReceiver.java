package ru.toolstrek.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Класс-приемник сообщений
 *
 * Created by EMararu on 03.07.2017.
 */
public class AbcBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Создание службы
        Intent serviceIntent = new Intent(context, AbcService.class);
        // Старт службы
        context.startService(serviceIntent);
    }

}
