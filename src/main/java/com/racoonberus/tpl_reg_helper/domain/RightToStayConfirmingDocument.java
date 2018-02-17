package com.racoonberus.tpl_reg_helper.domain;

import java.util.Date;

public class RightToStayConfirmingDocument {
    public enum Types {
        NONE,
        VISA, // виза
        RESIDENCE_PERMIT, // вид на жительство
        TMP_RESIDENCE_PERMIT // разрешение на временное проживание
    }

    private Types type = Types.VISA;

    private String series;
    private String identifier;

    private Date dateOfIssueDate;
    private Date validityTillDate;

    public Types getType() {
        return type;
    }

    public RightToStayConfirmingDocument setType(Types type) {
        this.type = type;
        return this;
    }

    public String getSeries() {
        return series;
    }

    public RightToStayConfirmingDocument setSeries(String series) {
        this.series = series;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public RightToStayConfirmingDocument setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public Date getDateOfIssueDate() {
        return dateOfIssueDate;
    }

    public RightToStayConfirmingDocument setDateOfIssueDate(Date dateOfIssueDate) {
        this.dateOfIssueDate = dateOfIssueDate;
        return this;
    }

    public Date getValidityTillDate() {
        return validityTillDate;
    }

    public RightToStayConfirmingDocument setValidityTillDate(Date validityTillDate) {
        this.validityTillDate = validityTillDate;
        return this;
    }
}
