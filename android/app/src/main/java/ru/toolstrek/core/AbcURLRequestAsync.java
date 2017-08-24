package ru.toolstrek.core;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Класс для асинхронной отправки запроса
 *
 * Created by EMararu on 21.08.2017.
 */
public class AbcURLRequestAsync extends AsyncTask<AbcURLRequestParam, Void, String> {

    private Context context;

    /**
     * Конструктор
     * @param app
     */
    public AbcURLRequestAsync(Context context) {
         super();
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(AbcURLRequestParam... param) {

        AbcURLRequest request = new AbcURLRequest(((AbcApplication)context).getCookieManager());
        String result = null;

        try {
            if (request.connect(param[0].getPath(), param[0].getMethod(), param[0].getParam()) <= 400) {
                result = request.getJSON();
            } else {
                result = request.getError();
            }
        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {

        } finally {
            request.disconnect();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
    }

}