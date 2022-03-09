package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.ParseImpl;

public class SrcIpStrategy {
    public SrcIpStrategy() {
    }

    JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has("src_ip")) {
            result.add("src_ip", new ParseImpl().extractSubValue(source, "src_ip"));
        }
        return result;
    }
}
