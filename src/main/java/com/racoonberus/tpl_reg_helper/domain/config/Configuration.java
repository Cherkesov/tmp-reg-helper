package com.racoonberus.tpl_reg_helper.domain.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Configuration {
    @JsonProperty("cell-aliases")
    private CellAliases aliases;

    public CellAliases getAliases() {
        return aliases;
    }

    public void setAliases(CellAliases aliases) {
        this.aliases = aliases;
    }
}
