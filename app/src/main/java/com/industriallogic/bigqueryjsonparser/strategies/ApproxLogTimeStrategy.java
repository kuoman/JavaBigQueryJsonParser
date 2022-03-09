package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.ParseImpl;

public class ApproxLogTimeStrategy {
    private final String keyname = "approxLogTime";
    private final ParseImpl parse;

    public ApproxLogTimeStrategy(ParseImpl parse) {
        this.parse = parse;
    }

    public JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has(keyname)) {
            result.add(keyname, parse.getMicroSeconds(source, keyname));
        }

        return result;
    }
}
