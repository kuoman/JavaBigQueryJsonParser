package com.industriallogic.bigqueryjsonparser.strategies;

import com.google.gson.JsonObject;
import com.industriallogic.bigqueryjsonparser.ParseImpl;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SrcIpStrategyTests {
    @Test
    public void shouldAddSrcIp() {
        // arrange
        JsonObject source = new JsonObject();

        JsonObject child = new JsonObject();
        child.addProperty("value", "162.3.63.50");
        source.add("src_ip", child);

        JsonObject result = new JsonObject();

        SrcIpStrategy srcIpStrategy = new SrcIpStrategy(new ParseImpl(), "src_ip");

        // act
        result = srcIpStrategy.parse(result, source);

        // assert
        assertThat(result.get("src_ip").getAsString(), is("162.3.63.50"));
    }

    @Test
    public void shouldAddDestIp() {
        // arrange
        JsonObject source = new JsonObject();

        JsonObject child = new JsonObject();
        child.addProperty("value", "162.3.63.50");
        source.add("dest_ip", child);

        JsonObject result = new JsonObject();

        SrcIpStrategy srcIpStrategy = new SrcIpStrategy(new ParseImpl(), "dest_ip");

        // act
        result = srcIpStrategy.parse(result, source);

        // assert
        assertThat(result.get("dest_ip").getAsString(), is("162.3.63.50"));
    }

    @Test
    public void shouldNotAddSrcIpIfDoesNotExist() {
        // arrange
        JsonObject source = new JsonObject();
        source.addProperty("src_ip2", "nope");

        JsonObject result = new JsonObject();

        SrcIpStrategy srcIpStrategy = new SrcIpStrategy(new ParseImpl(), "src_ip");

        // act
        result = srcIpStrategy.parse(result, source);

        // assert
        assertThat(result.has("src_ip"), is(false));
    }
}
