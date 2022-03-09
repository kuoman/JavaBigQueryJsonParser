package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
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

    private class MockParse extends NoOpParse {
        @Override
        public JsonElement extractValue(JsonObject source, String key) {
            return new JsonPrimitive("HI");
        }
    }
}
