package com.example.covid19passportapp.Models;

import com.google.firebase.database.Exclude;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Citizen {

    private String fullName;
    @Exclude
    private DateTime birthdate;
    private Passport passport;
    private List<Test> tests;
    private long birthdateMilis;

    public Citizen() {
    }

 public Citizen(String fullName, DateTime birthdate) {
        this.fullName = fullName;
        this.birthdate = birthdate;
        tests = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Exclude
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

    public long getBirthdateMilis() {
        return birthdateMilis;
    }

    public void setBirthdateMilis(long birthdateMilis) {
        this.birthdateMilis = birthdateMilis;
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
