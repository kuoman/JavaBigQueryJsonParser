package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class IocIpV4Strategy {
    private Parse parse;

    public IocIpV4Strategy(Parse parse) {
        this.parse = parse;
    }

    JsonObject parse(JsonObject result, JsonObject source) {

        if (source.has("ioc_ip_v4")) {
            result.add("ioc_ip_v4", parse.getValuesFromArray(source, "ioc_ip_v4"));
        }

        return result;
    }
}