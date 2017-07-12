package ru.toolstrek.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Основной экран приложения
 *
 * Created by EMararu on 03.07.2017.
 */
public class LoginActivity extends AppCompatActivity {

    private final String TAG = "Abc:LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "onCreate");
        // Создание родителя
        super.onCreate(savedInstanceState);
        // Установка экрана
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        // Отправка сообщения на запуск службы
        Intent broadcastIntent = new Intent("ru.toolstrek.action.restart");
        sendBroadcast(broadcastIntent);
    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}