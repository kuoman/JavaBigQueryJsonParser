package com.industriallogic.bigqueryjsonparser;


import com.google.gson.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParserTests {

    private final String json1File = "src/test/java/com/industriallogic/bigqueryjsonparser/json1.json";
    private final String json2File = "src/test/java/com/industriallogic/bigqueryjsonparser/json2.json";
    private final String json3File = "src/test/java/com/industriallogic/bigqueryjsonparser/json3.json";

    private final Parse parse = new Parse();

    @Test
    public void shouldFlatten1Level() {
        // arrange
        String input = "{\"level 1\": \"level1\", \"fields\":{}}";

        // act
        JsonObject flattened = parse.flatten(input);

        // assert
        assertThat(flattened.get("level 1").getAsString(), is("level1"));
    }

    @Test
    public void shouldFlatten2Level() {
        // arrange
        String input = "{ \"level 1\": \"level1\", \"fields\": {\"level 2\": \"level2bottom\"}}";

        // act
        JsonObject flattened = parse.flatten(input);

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
        JsonObject flattened = parse.flatten(input);

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
        JsonObject flattened = parse.flatten(fileContent);

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
        JsonObject flattened = parse.flatten(fileContent);

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
        JsonObject flattened = parse.flatten(fileContent);

        // assert
        assertThat(flattened.get("approxLogTime").getAsString(), is("1644862497000"));
        assertThat(flattened.get("fieldTypesVersion").getAsString(), is("2.0"));
        assertThat(flattened.get("id").getAsString(), is("bd5c39ae-d89e-460b-a659-01e2e62eaba1"));
        assertThat(flattened.get("forwarder").getAsString(), is("gcp-generator2.c.project-09fef8d782fec714.internal"));
        assertThat(flattened.has("fields"), is(false));
    }

    @Test
    public void shouldCleanUpDashCharacters() {
        // arrange
        JsonObject input = createJsonObject("{\"level-1\": \"level1\", \"fields\":{}}");

        // act
        JsonObject result = parse.removeSpecialCharacters(input);

        // assert
        assertThat(result.get("level_1").getAsString(), is("level1"));
        assertThat(result.has("level-1"), is(false));
    }

    @Test
    public void shouldCleanUpAtCharacters() {
        // arrange
        JsonObject input = createJsonObject("{\"@level1\": \"level1\", \"fields\":{}}");

        // act
        JsonObject result = parse.removeSpecialCharacters(input);

        // assert
        assertThat(result.get("level1").getAsString(), is("level1"));
        assertThat(result.has("@level1"), is(false));
    }

    @Test
    public void shouldCleanUpPeriodCharacters() {
        // arrange
        JsonObject input = createJsonObject("{\"level.1\": \"level1\", \"fields\":{}}");

        // act
        JsonObject result = parse.removeSpecialCharacters(input);

        // assert
        assertThat(result.get("level_1").getAsString(), is("level1"));
        assertThat(result.has("level.1"), is(false));
    }

    @Test
    public void shouldCleanUpMultipleSpecialCharacters() {
        // arrange
        JsonObject input = createJsonObject("{\"@level.1-\": \"level1\", \"fields\":{}}");

        // act
        JsonObject result = parse.removeSpecialCharacters(input);

        // assert
        assertThat(result.get("level_1_").getAsString(), is("level1"));
        assertThat(result.has("@level.1-"), is(false));
    }

    @Test
    public void shouldAddSrcIpField() throws IOException {
        // arrange
        Path filename = Path.of(json1File);
        String fileContent = Files.readString(filename);

        // act
        JsonObject result = parse.parse(fileContent);

        // assert
        assertThat(result.get("src_ip").getAsString(), is("162.3.63.50"));
    }

    @Test
    public void shouldAddDestIpField() throws IOException {
        // arrange
        Path filename = Path.of(json1File);
        String fileContent = Files.readString(filename);

        // act
        JsonObject result = parse.parse(fileContent);

        // assert
        assertThat(result.get("dest_ip").getAsString(), is("10.66.251.6"));
    }

    @Test
    public void shouldGetApproxLogTime() throws IOException {
        // arrange
        Path filename = Path.of(json2File);
        String fileContent = Files.readString(filename);

        // act
        JsonObject result = parse.parse(fileContent);

        // assert
        assertThat(result.get("approxLogTime").getAsLong(), is(1644862497000000L));
    }
    
    @Test
    public void shouldGetExabeamTimestamp() throws IOException {
        // arrange
        Path filename = Path.of(json1File);
        String fileContent = Files.readString(filename);

        // act
        JsonObject result = parse.parse(fileContent);

        // assert
        assertThat(result.get("exa_rsc_timestamp").getAsLong(), is(1644862504782000L));
    }

    @Test
    public void shouldGetIpV4Array() throws IOException {
        // arrange
        Path filename = Path.of(json1File);
        String fileContent = Files.readString(filename);

        // act
        JsonObject result = parse.parse(fileContent);

        // assert
        assertThat(result.get("ioc_ip_v4").getAsJsonArray().get(0).getAsString(), is("162.3.63.50"));
        assertThat(result.get("ioc_ip_v4").getAsJsonArray().get(1).getAsString(), is("10.66.251.6"));
        assertThat(result.get("ioc_ip_v4").getAsJsonArray().get(2).getAsString(), is("172.28.8.251"));
        assertThat(result.get("ioc_ip_v4").getAsJsonArray().get(3).getAsString(), is("170.232.3.56"));
    }

    @Test
    public void shouldGetValuesFromArray() {
        // arrange.
        JsonArray array = new JsonArray();

        JsonObject object1 = new JsonObject();
        object1.addProperty("value", "value1");

        array.add(object1);

        JsonObject object2 = new JsonObject();
        object2.addProperty("value", "value2");

        array.add(object2);

        JsonObject source = new JsonObject();
        source.add("id", array);

        // act.
        JsonElement result = parse.getValuesFromArray(source, "id");

        // assert.
        assertThat(result.getAsJsonArray().size(), is(2));
        assertThat(result.getAsJsonArray().get(0).getAsString(), is("value1"));
        assertThat(result.getAsJsonArray().get(1).getAsString(), is("value2"));
    }

    @Test
    public void shouldGetValueFromKey(){
        // arrange
        JsonObject source = new JsonObject();
        source.add("id", new JsonPrimitive("12345"));

        // act
        JsonElement result = parse.extractValue(source, "id");

        // assert
        assertThat(result.getAsString(), is("12345"));
    }


    @Test
    public void shouldGetValueFromNanoSeconds(){
        // arrange
        JsonObject source = new JsonObject();
        source.add("exa_rsc_timestamp", new JsonPrimitive("2022-02-14T18:15:04.782Z"));

        // act
        JsonElement result = parse.getNanoSeconds(source, "exa_rsc_timestamp");

        // assert
        assertThat(result.getAsLong(), is(1644862504782000L));
    }


    @Test
    public void shouldParseLogWithoutSourceIp() throws IOException {
        // arrange
        Path filename = Path.of(json3File);
        String fileContent = Files.readString(filename);

        // act
        JsonObject result = parse.parse(fileContent);

        // assert
        assertThat(result.get("approxLogTime").getAsString(), is("1644862497000000"));
       // assertThat(flattened.get("id").getAsString(), is("bd5c39ae-d89e-460b-a659-01e2e62eaba1"));
    }

    private JsonObject createJsonObject(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }
}
