package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class IdStrategy {

    private final String keyname = "id";
    private Parse parse;

    public IdStrategy(Parse parse) {

        this.parse = parse;
    }

    public JsonObject parse(JsonObject result, JsonObject source) {

        if (source.has(keyname)) {
            result.add(keyname, parse.extractValue(source, keyname));
        }

        return result;
    }
}
