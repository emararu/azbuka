package ru.toolstrek.core;

import java.util.Map;

/**
 * Параметры запроса на удаленный хост
 *
 * Created by EMararu on 21.08.2017.
 */
public class AbcURLRequestParam {

    final private String path;
    final private String method;
    final private Map<String, String> param;

    public AbcURLRequestParam(String path, String method, Map<String, String> param) {
        this.path = path;
        this.method = method;
        this.param = param;
    }

    public String getPath() {
        return this.path;
    }

    public String getMethod() {
        return this.method;
    }

    public Map<String, String> getParam() {
        return this.param;
    }

}