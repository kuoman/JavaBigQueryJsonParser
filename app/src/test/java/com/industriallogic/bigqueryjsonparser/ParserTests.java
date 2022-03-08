package com.industriallogic.bigqueryjsonparser;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParserTests {

    private final String json1File = "src/test/java/com/industriallogic/bigqueryjsonparser/json1.json";
    private final String json2File = "src/test/java/com/industriallogic/bigqueryjsonparser/json2.json";
    private final String json3File = "src/test/java/com/industriallogic/bigqueryjsonparser/json3.json";

    @Test
    public void shouldFlatten1Level() {
        // arrange
        String input = "{\"level 1\": \"level1\", \"fields\":{}}";

        // act
        JsonObject flattened = flatten(input);

        // assert

        assertThat(flattened.get("level 1").getAsString(), is("level1"));
    }

    @Test
    public void shouldFlatten2Level() {
        // arrange
        String input = "{ \"level 1\": \"level1\", \"fields\": {\"level 2\": \"level2bottom\"}}";

        // act
        JsonObject flattened = flatten(input);

        // assert
        assertThat(flattened.get("level 1").getAsString(), is("level1"));
        assertThat(flattened.get("level 2").getAsString(), is("level2bottom"));
        assertThat(flattened.has("fields"), is(false));
    }

    @Test
    public void shouldLoadAndFlatten() {
        // arrange
        String input = "{ \"level 1\": \"level1\", \"fields\": {\"level 2\": \"level2bottom\"}}";

        // act
        JsonObject flattened = flatten(input);

        // assert
        assertThat(flattened.get("level 1").getAsString(), is("level1"));
        assertThat(flattened.get("level 2").getAsString(), is("level2bottom"));
        assertThat(flattened.has("fields"), is(false));
    }

    @Test
    public void shouldLoadInJson1FileAndParseFields() throws IOException {
        // arrange
        Path filename = Path.of(json1File);
        String fileContent = Files.readString(filename);

        // act
        JsonObject flattened = flatten(fileContent);

        // assert
        assertThat(flattened.get("approxLogTime").getAsString(), is("1644862497000"));
        assertThat(flattened.get("fieldTypesVersion").getAsString(), is("2.0"));
        assertThat(flattened.get("id").getAsString(), is("92dbe5e0-faf6-442e-a6a9-4eed557d48a1"));
        assertThat(flattened.get("forwarder").getAsString(), is("gcp-generator2.c.project-09fef8d782fec714.internal"));
        assertThat(flattened.has("fields"), is(false));
    }

    @Test
    public void shouldLoadInJson2FileAndParseFields() throws IOException {
        // arrange
        Path filename = Path.of(json2File);
        String fileContent = Files.readString(filename);

        // act
        JsonObject flattened = flatten(fileContent);

        // assert
        assertThat(flattened.get("approxLogTime").getAsString(), is("1644862497000"));
        assertThat(flattened.get("fieldTypesVersion").getAsString(), is("2.0"));
        assertThat(flattened.get("id").getAsString(), is("757b7ee7-16bd-4f62-acff-ddecae390172"));
        assertThat(flattened.get("forwarder").getAsString(), is("gcp-generator2.c.project-09fef8d782fec714.internal"));
        assertThat(flattened.has("fields"), is(false));
    }

    @Test
    public void shouldLoadInJson3FileAndParseFields() throws IOException {
        // arrange
        Path filename = Path.of(json3File);
        String fileContent = Files.readString(filename);

        // act
        JsonObject flattened = flatten(fileContent);

        // assert
        assertThat(flattened.get("approxLogTime").getAsString(), is("1644862497000"));
        assertThat(flattened.get("fieldTypesVersion").getAsString(), is("2.0"));
        assertThat(flattened.get("id").getAsString(), is("bd5c39ae-d89e-460b-a659-01e2e62eaba1"));
        assertThat(flattened.get("forwarder").getAsString(), is("gcp-generator2.c.project-09fef8d782fec714.internal"));
        assertThat(flattened.has("fields"), is(false));
    }

    // flattened json

    // clean up removing special characters

    // iterate over fields to parse
        // if it's time, format
        // if it's an array format

    // save key/value in result

    @Test
    public void shouldCleanUpDashCharacters() {
        // arrange
        JsonObject input = createJsonObject("{\"level-1\": \"level1\", \"fields\":{}}");

        // act
        JsonObject result = removeSpecialCharacters(input);

        // assert
        assertThat(result.get("level_1").getAsString(), is("level1"));
        assertThat(result.has("level-1"), is(false));
    }

    @Test
    public void shouldCleanUpAtCharacters() {
        // arrange
        JsonObject input = createJsonObject("{\"@level1\": \"level1\", \"fields\":{}}");

        // act
        JsonObject result = removeSpecialCharacters(input);

        // assert
        assertThat(result.get("level1").getAsString(), is("level1"));
        assertThat(result.has("@level1"), is(false));
    }

    @Test
    public void shouldCleanUpPeriodCharacters() {
        // arrange
        JsonObject input = createJsonObject("{\"level.1\": \"level1\", \"fields\":{}}");

        // act
        JsonObject result = removeSpecialCharacters(input);

        // assert
        assertThat(result.get("level_1").getAsString(), is("level1"));
        assertThat(result.has("level.1"), is(false));
    }

    @Test
    public void shouldCleanUpMultipleSpecialCharacters() {
        // arrange
        JsonObject input = createJsonObject("{\"@level.1-\": \"level1\", \"fields\":{}}");

        // act
        JsonObject result = removeSpecialCharacters(input);

        // assert
        assertThat(result.get("level_1_").getAsString(), is("level1"));
        assertThat(result.has("@level.1-"), is(false));
    }


    @Test
    public void shouldAddSrcIpField() throws IOException {
        // arrange
        Path filename = Path.of(json1File);
        String fileContent = Files.readString(filename);
        JsonObject input = flatten(fileContent);

        // act
        JsonObject result = parse(input);

        // assert
        assertThat(result.get("src_ip").getAsString(), is("162.3.63.50"));
    }

    @Test
    public void shouldAddDestIpField() throws IOException {
        // arrange
        Path filename = Path.of(json1File);
        String fileContent = Files.readString(filename);
        JsonObject input = flatten(fileContent);

        // act
        JsonObject result = parse(input);

        // assert
        assertThat(result.get("dest_ip").getAsString(), is("10.66.251.6"));
    }

    @Test
    public void shouldGetApproxLogTime() throws IOException {
        // arrange
        Path filename = Path.of(json1File);
        String fileContent = Files.readString(filename);
        JsonObject input = flatten(fileContent);

        // act
        JsonObject result = parse(input);

        // assert
        assertThat(result.get("approxLogTime").getAsLong(), is(1644862497000000L));
    }
    
    @Test
    public void shouldGetExabeamTimestamp() throws IOException {
        // arrange
        Path filename = Path.of(json1File);
        String fileContent = Files.readString(filename);
        JsonObject input = flatten(fileContent);

        // act
        JsonObject result = parse(input);

        // assert
        assertThat(result.get("exa_rsc_timestamp").getAsString(), is("2022-02-14T18:15:04.782Z"));
    }

    private JsonObject parse(JsonObject source) {
        JsonObject result = new JsonObject();

        JsonObject cleanSource = removeSpecialCharacters(source);


        result.addProperty("exa_rsc_timestamp", extractStringValue(cleanSource, "exa_rsc_timestamp"));
        result.add("approxLogTime", getMicroseconds(cleanSource, "approxLogTime"));
        result.add("src_ip", extractSubValue(cleanSource, "src_ip"));
        result.add("dest_ip", extractSubValue(cleanSource, "dest_ip"));

        return result;
    }

    private String extractStringValue(JsonObject source, String key) {
        return source.get(key).getAsString();
    }

    private JsonElement getMicroseconds(JsonObject source, String keyName) {
        Long rawMilli = source.get(keyName).getAsLong();
        Long rawMicro = TimeUnit.MILLISECONDS.toMicros(rawMilli);
        return new JsonPrimitive(rawMicro);
    }

    private JsonElement extractSubValue(JsonObject source, String keyName) {
        return source.get(keyName).getAsJsonObject().get("value");
    }

    private JsonObject createJsonObject(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    private JsonObject removeSpecialCharacters(JsonObject input) {
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

    private JsonObject flatten(String input) {
        JsonObject result = JsonParser.parseString(input).getAsJsonObject();

        JsonObject fields = result.getAsJsonObject("fields");

        fields.keySet().forEach(key -> result.add(key, fields.get(key)));

        result.remove("fields");

        return result;
    }


}
