package com.industriallogic.bigqueryjsonparser;

import com.google.gson.*;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Parse {
    public JsonObject parse(String source) {
        JsonObject flatSource = flatten(source);

        JsonObject flatCleanSource = removeSpecialCharacters(flatSource);

        return parse(flatCleanSource);
    }

    private JsonObject parse(JsonObject source) {
        JsonObject result = new JsonObject();

        // required
        result.add("exa_rsc_timestamp", getNanoSeconds(source, "exa_rsc_timestamp"));

        result.add("approxLogTime", getMicroseconds(source, "approxLogTime"));

        // unknown
        result.add("ioc_ip_v4", getValuesFromArray(source, "ioc_ip_v4"));

        // optional
        if (source.has("src_ip")){
            result.add("src_ip", extractSubValue(source, "src_ip"));
        }

        if (source.has("dest_ip")){
            result.add("dest_ip", extractSubValue(source, "dest_ip"));
        }

        return result;
    }

    private JsonElement getValuesFromArray(JsonObject source, String key) {
        JsonArray result = new JsonArray();

        source.get(key).getAsJsonArray().forEach(element -> result.add(element.getAsJsonObject().get("value")));

        return result;
    }

    private JsonElement extractValue(JsonObject source, String key) {
        return source.get(key);
    }

    private JsonElement getNanoSeconds(JsonObject source, String key) {
        Instant exaRscTimestamp = Instant.parse(source.get(key).getAsString());

        Long epoch = exaRscTimestamp.getEpochSecond();
        Long micro = TimeUnit.NANOSECONDS.toMicros(exaRscTimestamp.getNano());
        Long rawMicro = TimeUnit.SECONDS.toMicros(epoch) + micro;

        return new JsonPrimitive(rawMicro);
    }

    private JsonElement getMicroseconds(JsonObject source, String keyName) {
        Long rawMilli = source.get(keyName).getAsLong();
        Long rawMicro = TimeUnit.MILLISECONDS.toMicros(rawMilli);
        return new JsonPrimitive(rawMicro);
    }

    private JsonElement extractSubValue(JsonObject source, String keyName) {
        return source.get(keyName).getAsJsonObject().get("value");
    }

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

    public JsonObject flatten(String input) {
        JsonObject result = JsonParser.parseString(input).getAsJsonObject();

        JsonObject fields = result.getAsJsonObject("fields");

        fields.keySet().forEach(key -> result.add(key, fields.get(key)));

        result.remove("fields");

        return result;
    }
}
