package com.example.covid19passportapp.Models;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Citizen {

    private String fullName;
    private DateTime birthdate;
    private Passport passport;
    private List<Test> tests;

    public Citizen() {
    }

    public Citizen(String fullName, DateTime birthdate, Passport passport, List<Test> tests) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        this.passport = passport;
        this.tests = tests;
    }

    public Citizen(String fullName, DateTime birthdate) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        passport = new Passport();
        tests = new ArrayList<>();
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

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "fullName='" + fullName + '\'' +
                ", birthdate=" + birthdate +
                ", passport=" + passport +
                ", tests=" + tests +
                '}';
    }
}
