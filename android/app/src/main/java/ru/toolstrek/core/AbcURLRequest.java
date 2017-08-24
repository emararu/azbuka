package ru.toolstrek.core;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Класс для запроса/передачи данных по указанному URL
 *
 * Created by EMararu on 09.08.2017.
 */
public class AbcURLRequest {

    private final CookieManager cookieManager;
    private final String COOKIES_HEADER = "Set-Cookie";

    private HttpURLConnection connection;

    /**
     * Конструктор
     * @param cookieManager Экземпляр объекта для обработки cookie-файлов
     */
    public AbcURLRequest(CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    private String castMapToJSON(Map<String, String> param) {
        JSONObject json = new JSONObject(param);
        return json.toString();
    }

    /**
     * Чтение результата запроса из входного потока
     * @param  is Входной поток
     * @return    Результат запроса
     * @throws IOException
     */
    private String getResultFromStream(InputStream is) throws IOException {
        // Ответ удаленного хоста на переданный запрос
        StringBuffer result = new StringBuffer();

        // Буферизация входного потока
        BufferedReader in = new BufferedReader(new InputStreamReader(is));

        // Чтение входного потока
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

    /**
     * Соединение с удаленным хостом
     * @param path   Путь к удаленному хосту
     * @param method Способ передачи данных удаленному хосту
     * @param param  Данные которые необходимо передать удаленному хосту(ключ=значение)
     * @return Статус-код ответа http-сервера на отправленный запрос зоединения
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public int connect(String path, String method, Map<String, String> param) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        // URL для соединения
        URL url = new URL(path);
        // Соединение с удаленным хостом
        this.connection = (HttpURLConnection) url.openConnection();

        // Если соединение выполняется по защищенному протоколу(SSL)
        if (this.connection instanceof HttpsURLConnection) {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, null, new SecureRandom());
            ((HttpsURLConnection)this.connection).setSSLSocketFactory(sc.getSocketFactory());
        }

        // Установка параметров соединения
        this.connection.setRequestMethod(method);
        this.connection.setConnectTimeout(10000);
        this.connection.setReadTimeout(10000);

        // Если есть cookie
        if (this.cookieManager.getCookieStore().getCookies().size() > 0) {
            this.connection.setRequestProperty("Cookie", TextUtils.join(";",  this.cookieManager.getCookieStore().getCookies()));
        }

        // Если есть параметры запроса
        if (param != null) {
            this.connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStream os = this.connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(castMapToJSON(param));
            writer.flush();
            writer.close();
            os.close();
        }

        // Установка соединения
        this.connection.connect();

        return this.connection.getResponseCode();
    }

    /**
     * Запрос данных результата соеднинения с удаленным хостом
     * @return Результат соединения с удаленным хостом
     * @throws IOException
     */
    public String getJSON() throws IOException  {
        // Запрос cookie в результате ответа удаленного хоста
        Map<String, List<String>> headerFields = this.connection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
        // Если в ответе есть cookie
        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                this.cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
        // Входной поток
        InputStream is = this.connection.getInputStream();

        return getResultFromStream(is);
    }

    /**
     * Запрос ошибки возникшей в результате обработки запроса
     * @return Ошибка возникшая в результате обработки запроса
     * @throws IOException
     */
    public String getError() throws IOException  {
        // Входной поток ошибок
        InputStream is = this.connection.getErrorStream();

        return getResultFromStream(is);
    }

    /**
     * Разрыв соединения с удаленным хостом
     */
    public void disconnect() {
        if (this.connection != null) {
            this.connection.disconnect();
        }
    }

}
