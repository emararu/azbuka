package ru.toolstrek.core;

//import android.app.Application
import android.text.TextUtils;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/**
 * Класс для запроса/передачи данных по указанному URL
 *
 * Created by EMararu on 09.08.2017.
 */

public class AbcURLRequest {

    private static AbcURLRequest instance = new AbcURLRequest();
    private static final String COOKIES_HEADER = "Set-Cookie";
    private static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    private AbcURLRequest() {
    }

    /*private String paramInLine(Map<String, String> param) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : param.entrySet())
        {
            result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.substring(1);
    }*/

    public static AbcURLRequest getInstance() {
        return instance;
    }

    public String request(String path, String method, String param) {

        String result = new String();
        HttpURLConnection connection = null;

        try {

            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            if (connection instanceof HttpsURLConnection) {
                SSLContext sc;
                sc = SSLContext.getInstance("TLS");
                sc.init(null, null, new java.security.SecureRandom());
                ((HttpsURLConnection)connection).setSSLSocketFactory(sc.getSocketFactory());
            }

            //if (method.equals("GET"))
            //connection.setRequestProperty("Authorization", "Basic "+Base64.encodeToString("kristina:kristina".getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));

            if (method.equals("DELETE")) {
                result += "[DELETE]";
                connection.setDoInput(true);
                connection.setDoOutput(false);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" );
            }
            connection.setRequestMethod(method);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            //connection.setDoInput(true);


            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
                result += "[cookie]";
                // While joining the Cookies, use ',' or ';' as needed. Most of the servers are using ';'
                connection.setRequestProperty("Cookie", TextUtils.join(";",  msCookieManager.getCookieStore().getCookies()));
            }

            if (param != null) {
                //connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //writer.write(paramInLine(param));
                result += "["+param+"]";
                writer.write(param);
                writer.flush();
                writer.close();
                os.close();
            }

            connection.connect();
            InputStream is;
            result += connection.getResponseCode();
            if (connection.getResponseCode() <= 400) {

                Map<String, List<String>> headerFields = connection.getHeaderFields();
                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                if (cookiesHeader != null) {
                    result += "[cookiesHeader]";
                    for (String cookie : cookiesHeader) {
                        msCookieManager.getCookieStore().add(null,HttpCookie.parse(cookie).get(0));
                    }
                }

                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result += inputLine;
            }

        } catch (Exception e) {
            result = e.toString();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

}
