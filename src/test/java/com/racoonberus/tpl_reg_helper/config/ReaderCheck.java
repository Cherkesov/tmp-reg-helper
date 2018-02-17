package com.racoonberus.tpl_reg_helper.config;

import com.racoonberus.tpl_reg_helper.domain.config.Configuration;
import org.junit.Assert;
import org.junit.Test;

public class ReaderCheck {
    @Test
    public void fromString() throws Exception {
        Configuration actual = Reader.fromString("" +
                "{\n" +
                "  \"cell-aliases\": {\n" +
                "    \"last-name\": [\n" +
                "      \"W13\",\n" +
                "      \"W69\"\n" +
                "    ],\n" +
                "    \"first-and-second-name\": [\n" +
                "      \"W15\",\n" +
                "      \"W71\"\n" +
                "    ],\n" +
                "    \"nationality\": [\n" +
                "      \"AA18\",\n" +
                "      \"AA74\"\n" +
                "    ]\n" +
                "  }" +
                "}");

        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getAliases());

        Assert.assertTrue(actual.getAliases().getLastName().length > 0);
        Assert.assertTrue(actual.getAliases().getFirstAndSecondName().length > 0);
        Assert.assertTrue(actual.getAliases().getNationality().length > 0);
    }
}