package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.industriallogic.bigqueryjsonparser.parseStrategies.IdStrategy;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IdStrategyTests {

    @Test
    public void shouldExtractId() throws IOException {
        // arrange
        JsonObject input = new JsonObject();
        input.add("id", new JsonPrimitive("12345"));
        JsonObject result = new JsonObject();

        IdStrategy idStrategy = new IdStrategy();

        // act
        result = idStrategy.parse(result, input);

        // assert
        assertThat(result.get("id").getAsString(), is("12345"));
    }

    @Test
    public void shouldNotExtractIfIdDoesNotExist() throws IOException {
        // arrange
        JsonObject input = new JsonObject();
        input.add("idz", new JsonPrimitive("12345"));
        JsonObject result = new JsonObject();

        IdStrategy idStrategy = new IdStrategy();

        // act
        result = idStrategy.parse(result, input);

        // assert

        assertThat(result.has("id"), is(false));

    }

}
