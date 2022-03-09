package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class ApproxLogTimeStrategy {
    private final String keyname = "approxLogTime";
    private final Parse parse;

    public ApproxLogTimeStrategy(Parse parse) {
        this.parse = parse;
    }

    public JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has(keyname)) {
            result.add(keyname, parse.getMicroSeconds(source, keyname));
        }

        return result;
    }
}
