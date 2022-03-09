package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.Parse;

public class IocIpV4Strategy {
    private final String keyName = "ioc_ip_v4";
    private final Parse parse;

    public IocIpV4Strategy(Parse parse) {
        this.parse = parse;
    }

    public JsonObject parse(JsonObject result, JsonObject source) {
        if (source.has(keyName)) {
            result.add(keyName, parse.getValuesFromArray(source, keyName));
        }

        return result;
    }
}
