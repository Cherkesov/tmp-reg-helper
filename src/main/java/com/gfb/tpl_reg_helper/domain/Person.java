package com.gfb.tpl_reg_helper.domain;

import java.util.Date;

public class Person {
    public enum Genders {MALE, FEMALE}

    public enum Purposes {
        BUSINESS_TRIP,
        TOURISM,
        BUSINESS,
        LEARNING,
        JOB,
        PRIVATE,
        TRANSIT,
        HUMANITARIAN,
        ANOTHER
    }

    private String lastName;
    private String firstName;
//    private String middleName;

    private String nationality;

    private Date birthday;
    private Genders gender = Genders.MALE;

    private PlaceOfBirth placeOfBirth;
    private IdentityDocument identityDocument;

    private RightToStayConfirmingDocument stayConfirmingDocument;

    private Purposes purpose = Purposes.TOURISM;

    private Date arrivalDate;
    private Date durationOfStay;

    private MigrationCard migrationCard;

    public String getLastName() {
        return lastName;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    public Person setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Person setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public Genders getGender() {
        return gender;
    }

    public Person setGender(Genders gender) {
        this.gender = gender;
        return this;
    }

    public PlaceOfBirth getPlaceOfBirth() {
        return placeOfBirth;
    }

    public Person setPlaceOfBirth(PlaceOfBirth placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public IdentityDocument getIdentityDocument() {
        return identityDocument;
    }

    public Person setIdentityDocument(IdentityDocument identityDocument) {
        this.identityDocument = identityDocument;
        return this;
    }

    public RightToStayConfirmingDocument getStayConfirmingDocument() {
        return stayConfirmingDocument;
    }

    public Person setStayConfirmingDocument(RightToStayConfirmingDocument stayConfirmingDocument) {
        this.stayConfirmingDocument = stayConfirmingDocument;
        return this;
    }

    public Purposes getPurpose() {
        return purpose;
    }

    public Person setPurpose(Purposes purpose) {
        this.purpose = purpose;
        return this;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Person setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
        return this;
    }

    public Date getDurationOfStay() {
        return durationOfStay;
    }

    public Person setDurationOfStay(Date durationOfStay) {
        this.durationOfStay = durationOfStay;
        return this;
    }

    public MigrationCard getMigrationCard() {
        return migrationCard;
    }

    public Person setMigrationCard(MigrationCard migrationCard) {
        this.migrationCard = migrationCard;
        return this;
    }
}
