package com.industriallogic.bigqueryjsonparser.strategies;

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

        IdStrategy idStrategy = new IdStrategy(new Parse());

        // act
        result = idStrategy.parse(result, input);

        // assert
        assertThat(result.get("id").getAsString(), is("12345"));
    }

    @Test
    public void shouldNotExtractIfIdDoesNotExist(){
        // arrange
        JsonObject input = new JsonObject();
        input.add("idz", new JsonPrimitive("12345"));
        JsonObject result = new JsonObject();

        IdStrategy idStrategy = new IdStrategy(new Parse());

        // act
        result = idStrategy.parse(result, input);

        // assert

        assertThat(result.has("id"), is(false));
    }
}
