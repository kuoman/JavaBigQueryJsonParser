package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApproxLogTimeStrategyTests {

    @Test
    public void shouldExtractApproxLogTime() {
        // arrange
        JsonObject source = new JsonObject();
        source.add("approxLogTime", new JsonPrimitive(1644862497000L));
        JsonObject result = new JsonObject();

        // act
        result = parse(result, source);

        // assert
        assertThat(result.get("approxLogTime").getAsString(), is("1644862497000000"));
    }

    private JsonObject parse(JsonObject result, JsonObject source) {
        result.addProperty("approxLogTime", "1644862497000000");
        return result;
    }
}
