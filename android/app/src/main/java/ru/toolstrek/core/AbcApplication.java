package ru.toolstrek.core;

import android.app.Application;
import android.util.Log;

import java.net.CookieManager;

/**
 * Базовый класс для работы с приложением
 *
 * Created by EMararu on 22.08.2017.
 */
public class AbcApplication extends Application {

    private final String TAG = "Abc:AbcApplication";

    // Экземпляр объекта для обработки cookie-файлов
    final private CookieManager cookieManager;
    int xx = 99;
    /**
     * Конструктор
     */
    public AbcApplication() {
        super();
        Log.i(TAG, "Constructor");
        this.cookieManager = new CookieManager();
    }

    /**
     * Возвращает менеджер для обработки cookie-файлов
     * @return Экземпляр объекта для обработки cookie-файлов
     */
    public CookieManager getCookieManager() {
        return this.cookieManager;
    }

    public int getInt() {return xx++; }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    /*@Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "onTerminate");
    }*/

}
