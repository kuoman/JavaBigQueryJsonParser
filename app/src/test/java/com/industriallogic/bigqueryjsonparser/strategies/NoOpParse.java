package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class NoOpParse implements Parse {
    @Override
    public JsonObject parse(String source) {
        return null;
    }

    @Override
    public JsonElement getValuesFromArray(JsonObject source, String key) {
        return null;
    }

    @Override
    public JsonElement extractValue(JsonObject source, String key) {
        return null;
    }

    @Override
    public JsonElement getNanoSeconds(JsonObject source, String key) {
        return null;
    }

    @Override
    public JsonElement getMicroSeconds(JsonObject source, String keyName) {
        return null;
    }

    @Override
    public JsonElement extractSubValue(JsonObject source, String keyName) {
        return null;
    }

    @Override
    public JsonObject removeSpecialCharacters(JsonObject input) {
        return null;
    }

    @Override
    public JsonObject flatten(String input) {
        return null;
    }
}
