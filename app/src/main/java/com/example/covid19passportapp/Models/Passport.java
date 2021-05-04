package com.example.covid19passportapp.Models;

import org.joda.time.DateTime;

public class Passport {

    private String id;
    private String fullName;
    private DateTime birthdate;
    private String country;
    private DateTime vaccinationDate;
    private String vaccineType;
    private DateTime immuneUntil;

    public Passport() {
    }

    public Passport(String id, String fullName, DateTime birthdate, String country, DateTime vaccinationDate, String vaccineType, DateTime immuneUntil) {
        this.id = id;
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.country = country;
        this.vaccinationDate = vaccinationDate;
        this.vaccineType = vaccineType;
        this.immuneUntil = immuneUntil;
    }

    public Passport(String id, String country, DateTime vaccinationDate, String vaccineType, DateTime immuneUntil) {
        this.id = id;
        this.country = country;
        this.vaccinationDate = vaccinationDate;
        this.vaccineType = vaccineType;
        this.immuneUntil = immuneUntil;
    }

    public Passport(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public DateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(DateTime birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DateTime getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(DateTime vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public DateTime getImmuneUntil() {
        return immuneUntil;
    }

    public void setImmuneUntil(DateTime immuneUntil) {
        this.immuneUntil = immuneUntil;
    }
}
