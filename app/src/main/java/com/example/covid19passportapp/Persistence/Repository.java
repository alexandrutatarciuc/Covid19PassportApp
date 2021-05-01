package com.example.covid19passportapp.Persistence;

import androidx.constraintlayout.solver.Cache;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.covid19passportapp.Models.Passport;
import com.example.covid19passportapp.Models.Test;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repository {

    private static Repository instance;

    private MutableLiveData<List<Test>> tests;
    private Passport passport;

    private Repository()
    {
        tests = new MutableLiveData<>();
        List<Test> mockUpTests = new ArrayList<Test>();
        mockUpTests.add(new Test(1, DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(2, DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(3, DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(4, DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(5, DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(6, DateTime.now(), "UNKNOWN"));
        mockUpTests.add(new Test(7, DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(8, DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(9, DateTime.now(), "POSITIVE"));
        mockUpTests.add(new Test(10, DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(11, DateTime.now(), "NEGATIVE"));
        tests.setValue(mockUpTests);

    }

    public static synchronized Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    public LiveData<List<Test>> getTests() {
        return tests;
    }

    public void addTest(Test test) {
        test.setID( Objects.requireNonNull(tests.getValue()).get(tests.getValue().size() - 1).getID() + 1); //sets id (last id + 1)
        Objects.requireNonNull(tests.getValue()).add(test);
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public boolean isPassportCreated() {
        return passport != null;
    }
}
