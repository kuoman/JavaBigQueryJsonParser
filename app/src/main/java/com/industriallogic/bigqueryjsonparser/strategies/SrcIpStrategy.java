package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.ParseImpl;

public class SrcIpStrategy {
    private final String keyName = "src_ip";

    public SrcIpStrategy() {
    }

    JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has(keyName)) {
            result.add(keyName, new ParseImpl().extractSubValue(source, keyName));
        }
        return result;
    }
}
