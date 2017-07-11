package ru.toolstrek.activity;

//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Context;
import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//import ru.toolstrek.core.AbcService;

/**
 * Основной экран приложения
 *
 * Created by EMararu on 03.07.2017.
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "Abc:MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "onCreate");
        // Создание родителя
        super.onCreate(savedInstanceState);
        // Установка экрана
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        // Отправка сообщения на запуск службы
        Intent broadcastIntent = new Intent("ru.toolstrek.action.restart");
        sendBroadcast(broadcastIntent);
    }

}
