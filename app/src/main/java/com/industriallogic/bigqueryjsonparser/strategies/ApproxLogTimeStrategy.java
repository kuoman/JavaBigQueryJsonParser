package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class ApproxLogTimeStrategy {
    private final Parse parse;

    public ApproxLogTimeStrategy(Parse parse) {
        this.parse = parse;
    }

    JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has("approxLogTime")) {
            result.add("approxLogTime", parse.getMicroseconds(source, "approxLogTime"));
        }

        return result;
    }
}
