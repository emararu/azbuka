package ru.toolstrek.activity;

import ru.toolstrek.user.AbcUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Запуск системы с последующим определением стартового экрана
 *
 * Created by EMararu on 11.08.2017.
 */

public class StartActivity extends AppCompatActivity {

    private final String TAG = "Abc:StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "onCreate");
        // Создание родителя
        super.onCreate(savedInstanceState);

        if (AbcUser.check == 1) {
            // Отправка сообщения на запуск службы
            Intent broadcastIntent = new Intent("ru.toolstrek.action.restart");
            sendBroadcast(broadcastIntent);
            // Переход на основной экран
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            // Переход на экран ввода логина/пароля для входа
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

}
