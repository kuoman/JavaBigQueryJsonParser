package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.industriallogic.bigqueryjsonparser.ParseImpl;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExaRscTimestampStrategyTests {
    private final ExaRscTimestampStrategy exaRscTimestampStrategy = new ExaRscTimestampStrategy(new ParseImpl());

    @Test
    public void shouldExtractId() {
        // arrange
        JsonObject source = new JsonObject();
        source.add("exa_rsc_timestamp", new JsonPrimitive("2022-02-14T18:15:04.782Z"));
        JsonObject result = new JsonObject();

        // act
        result = exaRscTimestampStrategy.parse(result, source);

        // assert
        assertThat(result.get("exa_rsc_timestamp").getAsLong(), is(1644862504782000L));
    }

    @Test
    public void shouldNotExtractIfIdDoesNotExist(){
        // arrange
        JsonObject source = new JsonObject();
        source.add("exa_rsc_timestampx", new JsonPrimitive("2022-02-14T18:15:04.782Z"));
        JsonObject result = new JsonObject();

        // act
        result = exaRscTimestampStrategy.parse(result, source);

        // assert

        assertThat(result.has("exa_rsc_timestamp"), is(false));
    }
}
