package com.industriallogic.bigqueryjsonparser.parseStrategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class IdStrategy {

    private final String fieldName = "id";
    private Parse parse;

    public IdStrategy(Parse parse) {

        this.parse = parse;
    }

    public JsonObject parse(JsonObject result, JsonObject source) {

        if (source.has(fieldName)) {
            result.add(fieldName, parse.extractValue(source, fieldName));
        }

        return result;
    }
}
