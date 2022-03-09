package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class SrcIpStrategy {
    private final String keyName;
    private final Parse parse;

    public SrcIpStrategy(Parse parse, String keyName) {
        this.parse = parse;
        this.keyName = keyName;
    }

    JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has(keyName)) {
            result.add(keyName, parse.extractSubValue(source, keyName));
        }
        return result;
    }
}
