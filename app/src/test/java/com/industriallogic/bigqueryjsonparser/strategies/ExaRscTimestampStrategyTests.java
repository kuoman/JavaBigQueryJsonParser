package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.industriallogic.bigqueryjsonparser.Parse;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExaRscTimestampStrategyTests {
    @Test
    public void shouldExtractId() {
        // arrange
        JsonObject input = new JsonObject();
        input.add("exa_rsc.timestamp", new JsonPrimitive("2022-02-14T18:15:04.782Z"));
        JsonObject result = new JsonObject();

        // act
        result = parse(result, input);

        // assert
        assertThat(result.get("exa_rsc.timestamp").getAsLong(), is(1644862504782000L));
    }

    private JsonObject parse(JsonObject result, JsonObject source) {
        Parse parse = new Parse();

        result.add("exa_rsc.timestamp", parse.getNanoSeconds(source, "exa_rsc.timestamp"));
        return result;
    }
}
