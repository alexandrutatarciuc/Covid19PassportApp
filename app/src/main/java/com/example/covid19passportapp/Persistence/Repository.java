package com.example.covid19passportapp.Persistence;

import android.util.Log;

import androidx.constraintlayout.solver.Cache;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.covid19passportapp.Models.Citizen;
import com.example.covid19passportapp.Models.Passport;
import com.example.covid19passportapp.Models.Test;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repository {


    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference rootRef = database.getReference("covid19passportapp-default-rtdb");
    private static Repository instance;

    private MutableLiveData<List<Test>> tests;
    private Passport passport;

    private Repository()
    {
        tests = new MutableLiveData<>();
        List<Test> mockUpTests = new ArrayList<Test>();
        mockUpTests.add(new Test( DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test( DateTime.now(), "UNKNOWN"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test( DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "POSITIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        tests.setValue(mockUpTests);

        //database
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
        //test.setID( Objects.requireNonNull(tests.getValue()).get(tests.getValue().size() - 1).getID() + 1); //sets id (last id + 1)
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

    public void addCitizen(Citizen citizen) {
        rootRef.child("citizens").push().setValue(citizen);
        Log.d("REPOSITORY",citizen.toString());
        Log.d("REPOSITORY","I GOT HERE");
    }
}
