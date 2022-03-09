package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;
import com.industriallogic.bigqueryjsonparser.ParseImpl;

public class SrcIpStrategy {
    private final String keyName = "src_ip";
    private final Parse parse;

    public SrcIpStrategy(Parse parse) {
        this.parse = parse;
    }

    JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has(keyName)) {
            result.add(keyName, parse.extractSubValue(source, keyName));
        }
        return result;
    }
}
