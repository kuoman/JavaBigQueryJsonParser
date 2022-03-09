package com.industriallogic.bigqueryjsonparser.parseStrategies;

import com.google.gson.JsonObject;

public class IdStrategy {

    private String fieldName = "id";

    public JsonObject parse(JsonObject result, JsonObject source) {

        if (source.has(fieldName)) {
            result.add(fieldName, source.get(fieldName));
        }

        return result;
    }
}
