package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApproxLogTimeStrategyTests {

    private final ApproxLogTimeStrategy approxLogTimeStrategy = new ApproxLogTimeStrategy();

    @Test
    public void shouldExtractApproxLogTime() {
        // arrange
        JsonObject source = new JsonObject();
        source.add("approxLogTime", new JsonPrimitive(1644862497000L));
        JsonObject result = new JsonObject();

        // act
        result = approxLogTimeStrategy.parse(result, source);

        // assert
        assertThat(result.get("approxLogTime").getAsString(), is("1644862497000000"));
    }

    @Test
    public void shouldNotExtractApproxLogTimeIfDoesNotExist() {
        // arrange
        JsonObject source = new JsonObject();
        source.add("approxLogTime2", new JsonPrimitive(1644862497000L));
        JsonObject result = new JsonObject();

        // act
        result = approxLogTimeStrategy.parse(result, source);

        // assert
        assertThat(result.has("approxLogTime"), is(false));
    }
}
