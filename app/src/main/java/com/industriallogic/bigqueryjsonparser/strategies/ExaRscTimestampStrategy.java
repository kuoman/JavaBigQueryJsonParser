package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class ExaRscTimestampStrategy {
    private Parse parse;

    public ExaRscTimestampStrategy(Parse parse) {
        this.parse = parse;
    }

    public JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has("exa_rsc.timestamp")) {
            result.add("exa_rsc.timestamp", parse.getNanoSeconds(source, "exa_rsc.timestamp"));
        }

        return result;
    }
}
