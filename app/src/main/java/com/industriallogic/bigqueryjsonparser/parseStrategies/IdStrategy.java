package com.industriallogic.bigqueryjsonparser.parseStrategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class IdStrategy {

    private String fieldName = "id";

    public JsonObject parse(JsonObject result, JsonObject source) {

        if (source.has(fieldName)) {
            Parse parse = new Parse();

            result.add(fieldName, parse.extractValue(source, fieldName));
        }

        return result;
    }
}
