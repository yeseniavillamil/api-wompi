package com.wompi.api.utils.json;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JSONUtils {

    private JSONUtils() {
    }

    public static <T> String pasarAJson(T objeto) {
        final Gson gson = new Gson();
        return gson.toJson(objeto);
    }

    public static <T> T pasarAObjeto(String json,Class<T> tipo) {
        final Gson gson = new Gson();
        return gson.fromJson(json, (Type)tipo);
    }
}
