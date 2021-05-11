package com.example.covid19passportapp.Models;

import com.google.firebase.database.Exclude;

import org.joda.time.DateTime;

public class Passport {

    private String id;
    private String country;
    @Exclude
    private DateTime vaccinationDate;
    private String vaccineType;
    @Exclude
    private DateTime immuneUntil;
    private long vaccinationDateMilis;
    private long immuneUntilMilis;

    public Passport() {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Exclude
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

    @Exclude
    public DateTime getImmuneUntil() {
        return immuneUntil;
    }

    public void setImmuneUntil(DateTime immuneUntil) {
        this.immuneUntil = immuneUntil;
    }

    public long getVaccinationDateMilis() {
        return vaccinationDateMilis;
    }

    public void setVaccinationDateMilis(long vaccinationDateMilis) {
        this.vaccinationDateMilis = vaccinationDateMilis;
    }

    public long getImmuneUntilMilis() {
        return immuneUntilMilis;
    }

    public void setImmuneUntilMilis(long immuneUntilMilis) {
        this.immuneUntilMilis = immuneUntilMilis;
    }
}
