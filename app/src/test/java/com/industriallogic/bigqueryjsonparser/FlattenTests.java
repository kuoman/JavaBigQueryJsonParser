package com.industriallogic.bigqueryjsonparser;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

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
    public void shouldLoadInJsonFileAndParseFields() throws FileNotFoundException {
        // arrange
//        File initialFile = new File(URI.create("json1.json"));
//        Reader reader = new FileReader(initialFile);
//        JsonElement jsonElement = JsonParser.parseReader(reader);
//
//        System.out.println(jsonElement);
        // act

        // assert

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


        Set<String> strings = fields.keySet();

        for (String key: strings) {
            result.add(key, fields.get(key));
        }

        result.remove("fields");

        return result;
    }
}
