package com.racoonberus.tpl_reg_helper.domain.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CellAliases {
    @JsonProperty("last-name")
    private String[] lastName;

    @JsonProperty("first-and-second-name")
    private String[] firstAndSecondName;

    @JsonProperty("nationality")
    private String[] nationality;

    public String[] getLastName() {
        return lastName;
    }

    public void setLastName(String[] lastName) {
        this.lastName = lastName;
    }

    public String[] getFirstAndSecondName() {
        return firstAndSecondName;
    }

    public void setFirstAndSecondName(String[] firstAndSecondName) {
        this.firstAndSecondName = firstAndSecondName;
    }

    public String[] getNationality() {
        return nationality;
    }

    public void setNationality(String[] nationality) {
        this.nationality = nationality;
    }
}
