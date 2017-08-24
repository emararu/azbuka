package ru.toolstrek.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ru.toolstrek.core.AbcApplication;

/**
 * Экран входа в приложение
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
        // Скрытие ActionBar
        getSupportActionBar().hide();
    }

    public void onClickLogin(View view) {

        String login    = ((TextView)findViewById(R.id.editLogin)).getText().toString();
        String password = ((TextView)findViewById(R.id.editPassword)).getText().toString();

        //Log.i(TAG, "onClickLogin=" + ((AbcApplication)getApplicationContext()).getInt());

        if (!login.isEmpty() && !password.isEmpty()) {
            // Отправка сообщения на запуск службы
            Intent broadcastIntent = new Intent("ru.toolstrek.action.restart");
            sendBroadcast(broadcastIntent);
            // Переход на основной экран
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

}
