package com.wompi.api.utils.objects;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wompi.api.utils.GeneralUtil;

import java.util.HashMap;
import java.util.Map;

public class MapperUtil {

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private MapperUtil(){
        GeneralUtil.notAllowInstantiation();
    }

    public static <T> T convertMapToObject(Map<String, Object> map, Class<T> type) {
        return MapperUtil.mapper.convertValue(map, type);
    }

    public static Map<String, Object> convertToObjectsMap(Map<String, String> flatMap) {
        Map<String, Object> nestedMap = new HashMap<>();

        for (Map.Entry<String, String> entry : flatMap.entrySet()) {
            String[] keys = entry.getKey().split("\\.");

            Map<String, Object> currentMap = nestedMap;
            for (int i = 0; i < keys.length - 1; i++) {
                currentMap = (Map<String, Object>) currentMap.computeIfAbsent(keys[i], k -> new HashMap<>());
            }

            currentMap.put(keys[keys.length - 1], entry.getValue());
        }

        return nestedMap;
    }
}

