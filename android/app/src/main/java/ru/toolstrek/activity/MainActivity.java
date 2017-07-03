package ru.toolstrek.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.toolstrek.core.AbcService;

/**
 * Основной экран приложения
 *
 * Created by EMararu on 03.07.2017.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Создание родителя
        super.onCreate(savedInstanceState);
        // Установка экрана
        setContentView(R.layout.activity_main);
        // Создание службы
        Intent serviceIntent = new Intent(this, AbcService.class);
        // Старт службы
        startService(serviceIntent);
    }
}
