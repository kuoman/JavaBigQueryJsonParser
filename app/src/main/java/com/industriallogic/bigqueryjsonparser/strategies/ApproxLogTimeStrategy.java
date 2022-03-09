package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class ApproxLogTimeStrategy {

    private Parse parse;

    public ApproxLogTimeStrategy() {
        parse = new Parse();
    }

    JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has("approxLogTime")) {
            result.add("approxLogTime", parse.getMicroseconds(source, "approxLogTime"));
        }

        return result;
    }
}
