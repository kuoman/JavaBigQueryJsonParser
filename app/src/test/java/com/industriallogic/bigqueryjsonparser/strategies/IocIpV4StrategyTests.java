package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IocIpV4StrategyTests {

    private final IocIpV4Strategy iocIpV4Strategy = new IocIpV4Strategy(new MockParse());

    @Test
    public void shouldGetSubArray() {
        // arrange
        JsonObject source = new JsonObject();
        source.add("ioc_ip_v4", new JsonPrimitive(""));
        JsonObject result = new JsonObject();

        // act
        result = iocIpV4Strategy.parse(result, source);

        // assert
        assertThat(result.get("ioc_ip_v4").getAsString(), is("HI"));
    }

    @Test
    public void shouldNotGetSubArrayIfKeyDoesNotExist() {
        // arrange
        JsonObject source = new JsonObject();
        source.add("ioc_ip_v4x", new JsonPrimitive(""));
        JsonObject result = new JsonObject();

        // act
        result = iocIpV4Strategy.parse(result, source);

        // assert
        assertThat(result.has("ioc_ip_v4"), is(false));
    }

    private class MockParse extends NoOpParse {
        @Override
        public JsonElement getValuesFromArray(JsonObject source, String key) {
            return new JsonPrimitive("HI");
        }
    }
}
