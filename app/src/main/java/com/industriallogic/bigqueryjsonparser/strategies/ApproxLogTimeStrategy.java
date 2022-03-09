package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class ApproxLogTimeStrategy {
    public ApproxLogTimeStrategy() {
    }

    JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has("approxLogTime")) {
            result.add("approxLogTime", new Parse().getMicroseconds(source, "approxLogTime"));
        }

        return result;
    }
}
