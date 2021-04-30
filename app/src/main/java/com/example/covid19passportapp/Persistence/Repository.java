package com.example.covid19passportapp.Persistence;

import androidx.constraintlayout.solver.Cache;

import com.example.covid19passportapp.Models.Passport;
import com.example.covid19passportapp.Models.Test;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository instance;

    private List<Test> tests;
    private Passport passport;

    private Repository()
    {
        tests = new ArrayList<>();
    }

    public static synchronized Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public void addTest(Test test) {
        tests.add(test);
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }
}
