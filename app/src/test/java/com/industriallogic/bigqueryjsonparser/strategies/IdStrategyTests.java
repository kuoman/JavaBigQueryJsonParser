package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.industriallogic.bigqueryjsonparser.Parse;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IdStrategyTests {

    @Test
    public void shouldExtractId(){
        // arrange
        JsonObject input = new JsonObject();
        input.add("id", new JsonPrimitive("12345"));
        JsonObject result = new JsonObject();

        IdStrategy idStrategy = new IdStrategy(new MockParse());

        // act
        result = idStrategy.parse(result, input);

        // assert
        assertThat(result.get("id").getAsString(), is("HI"));
    }

    @Test
    public void shouldNotExtractIfIdDoesNotExist(){
        // arrange
        JsonObject input = new JsonObject();
        input.add("idz", new JsonPrimitive("12345"));
        JsonObject result = new JsonObject();

        IdStrategy idStrategy = new IdStrategy(new MockParse());

        // act
        result = idStrategy.parse(result, input);

        // assert

        assertThat(result.has("id"), is(false));
    }

    private class MockParse implements Parse{

        @Override
        public JsonObject parse(String source) {
            return null;
        }

        @Override
        public JsonElement getValuesFromArray(JsonObject source, String key) {
            return null;
        }

        @Override
        public JsonElement extractValue(JsonObject source, String key) {
            return new JsonPrimitive("HI");
        }

        @Override
        public JsonElement getNanoSeconds(JsonObject source, String key) {
            return null;
        }

        @Override
        public JsonElement getMicroSeconds(JsonObject source, String keyName) {
            return null;
        }

        @Override
        public JsonElement extractSubValue(JsonObject source, String keyName) {
            return null;
        }

        @Override
        public JsonObject removeSpecialCharacters(JsonObject input) {
            return null;
        }

        @Override
        public JsonObject flatten(String input) {
            return null;
        }
    }
}
