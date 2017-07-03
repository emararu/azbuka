package ru.toolstrek.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;

/**
 * Загрузка приложения как сервиса
 *
 * Created by EMararu on 03.07.2017.
 */
public class AbcService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // something to do when the service is created
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if ((flags & START_FLAG_RETRY) == 0) {
            // TODO Если это повторный запуск, выполнить какие-то действия.
        }
        else {
            // TODO Альтернативные действия в фоновом режиме.
        }

        return Service.START_STICKY;
    }

}
