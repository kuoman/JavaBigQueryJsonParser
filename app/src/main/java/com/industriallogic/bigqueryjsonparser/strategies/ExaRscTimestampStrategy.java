package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class ExaRscTimestampStrategy {
    public final String keyname = "exa_rsc.timestamp";
    private final Parse parse;

    public ExaRscTimestampStrategy(Parse parse) {
        this.parse = parse;
    }

    public JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has(keyname)) {
            result.add(keyname, parse.getNanoSeconds(source, keyname));
        }

        return result;
    }
}