package com.industriallogic.bigqueryjsonparser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface Parse {
    JsonObject parse(String source);

    JsonElement getValuesFromArray(JsonObject source, String key);

    JsonElement extractValue(JsonObject source, String key);

    JsonElement getNanoSeconds(JsonObject source, String key);

    JsonElement getMicroSeconds(JsonObject source, String keyName);

    JsonElement extractSubValue(JsonObject source, String keyName);

    JsonObject removeSpecialCharacters(JsonObject input);

    JsonObject flatten(String input);
}
