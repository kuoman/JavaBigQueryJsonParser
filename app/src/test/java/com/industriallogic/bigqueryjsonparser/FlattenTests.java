package com.industriallogic.bigqueryjsonparser;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FlattenTests {

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
    public void shouldLoadInJsonFileAndParseFields() throws IOException {
        // arrange
        Path filename = Path.of("src/test/java/com/industriallogic/bigqueryjsonparser/json1.json");
        String fileContent = Files.readString(filename);

        // act
        JsonObject flattened = flatten(fileContent);

        // assert
        assertThat(flattened.get("approxLogTime").getAsString(), is("1644862497000"));
        assertThat(flattened.get("fieldTypesVersion").getAsString(), is("2.0"));
        assertThat(flattened.get("forwarder").getAsString(), is("gcp-generator2.c.project-09fef8d782fec714.internal"));
        assertThat(flattened.has("fields"), is(false));
    }

    @Test
    public void shouldPrintFile() throws IOException {
        Path filename = Path.of("src/test/java/com/industriallogic/bigqueryjsonparser/json1.json");
        String fileContent = Files.readString(filename);
        System.out.println(fileContent);
    }

    private JsonObject flatten(String input) {

        JsonObject result = JsonParser.parseString(input).getAsJsonObject();

        JsonObject fields = result.getAsJsonObject("fields");

        for (String key: fields.keySet()) {
            result.add(key, fields.get(key));
        }

        result.remove("fields");

        return result;
    }
}
