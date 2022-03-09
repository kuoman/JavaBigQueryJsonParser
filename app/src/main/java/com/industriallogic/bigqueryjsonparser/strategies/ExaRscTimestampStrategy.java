package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.ParseImpl;

public class ExaRscTimestampStrategy {
    private final String keyName = "exa_rsc_timestamp";
    private final ParseImpl parse;

    public ExaRscTimestampStrategy(ParseImpl parse) {
        this.parse = parse;
    }

    public JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has(keyName)) {
            result.add(keyName, parse.getNanoSeconds(source, keyName));
        }

        return result;
    }
}
