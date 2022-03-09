package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SrcIpStrategyTests {
    private final SrcIpStrategy srcIpStrategy = new SrcIpStrategy();

    @Test
    public void shouldAddSrcIp() {
        // arrange
        JsonObject source = new JsonObject();

        JsonObject child = new JsonObject();
        child.addProperty("value", "162.3.63.50");
        source.add("src_ip", child);

        JsonObject result = new JsonObject();

        // act
        result = srcIpStrategy.parse(result, source);

        // assert
        assertThat(result.get("src_ip").getAsString(), is("162.3.63.50"));
    }

    @Test
    public void shouldNotAddSrcIpIfDoesNotExist() {
        // arrange
        JsonObject source = new JsonObject();
        source.addProperty("src_ip2", "nope");

        JsonObject result = new JsonObject();

        // act
        result = srcIpStrategy.parse(result, source);

        // assert
        assertThat(result.has("src_ip"), is(false));
    }
}
