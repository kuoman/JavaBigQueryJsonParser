package com.industriallogic.bigqueryjsonparser;

import com.google.gson.*;
import com.industriallogic.bigqueryjsonparser.strategies.ApproxLogTimeStrategy;
import com.industriallogic.bigqueryjsonparser.strategies.ExaRscTimestampStrategy;
import com.industriallogic.bigqueryjsonparser.strategies.IocIpV4Strategy;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class ParseImpl implements Parse {

    @Override
    public JsonObject parse(String source) {
        JsonObject flatSource = flatten(source);

        JsonObject flatCleanSource = removeSpecialCharacters(flatSource);

        return parse(flatCleanSource);
    }

    private JsonObject parse(JsonObject source) {
        JsonObject result = new JsonObject();

        // required
        new ExaRscTimestampStrategy(this).parse(result, source);
        new ApproxLogTimeStrategy(this).parse(result, source);

        // unknown
        new IocIpV4Strategy(this).parse(result, source);

        // optional
        if (source.has("src_ip")){
            result.add("src_ip", extractSubValue(source, "src_ip"));
        }

        if (source.has("dest_ip")){
            result.add("dest_ip", extractSubValue(source, "dest_ip"));
        }

        return result;
    }

    @Override
    public JsonElement getValuesFromArray(JsonObject source, String key) {
        JsonArray result = new JsonArray();

        source.get(key).getAsJsonArray().forEach(element -> result.add(element.getAsJsonObject().get("value")));

        return result;
    }

    @Override
    public JsonElement extractValue(JsonObject source, String key) {
        return source.get(key);
    }

    @Override
    public JsonElement getNanoSeconds(JsonObject source, String key) {
        Instant exaRscTimestamp = Instant.parse(source.get(key).getAsString());

        long epoch = exaRscTimestamp.getEpochSecond();
        long micro = TimeUnit.NANOSECONDS.toMicros(exaRscTimestamp.getNano());
        long rawMicro = TimeUnit.SECONDS.toMicros(epoch) + micro;

        return new JsonPrimitive(rawMicro);
    }

    @Override
    public JsonElement getMicroSeconds(JsonObject source, String keyName) {
        long rawMilli = source.get(keyName).getAsLong();
        long rawMicro = TimeUnit.MILLISECONDS.toMicros(rawMilli);
        return new JsonPrimitive(rawMicro);
    }

    @Override
    public JsonElement extractSubValue(JsonObject source, String keyName) {
        return source.get(keyName).getAsJsonObject().get("value");
    }

    @Override
    public JsonObject removeSpecialCharacters(JsonObject input) {
        JsonObject result = new JsonObject();

        input.keySet().forEach(key -> result.add(removeSpecialCharactersFrom(key), input.get(key)));

        return result;
    }

    private String removeSpecialCharactersFrom(String key) {
        return key
                .replaceAll("\\.", "_")
                .replaceAll("-", "_")
                .replaceAll("@", "");
    }

    @Override
    public JsonObject flatten(String input) {
        JsonObject result = JsonParser.parseString(input).getAsJsonObject();

        JsonObject fields = result.getAsJsonObject("fields");

        fields.keySet().forEach(key -> result.add(key, fields.get(key)));

        result.remove("fields");

        return result;
    }
}
