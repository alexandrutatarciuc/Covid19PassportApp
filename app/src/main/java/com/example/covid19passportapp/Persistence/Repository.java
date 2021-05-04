package com.example.covid19passportapp.Persistence;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.Cache;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.covid19passportapp.Models.Citizen;
import com.example.covid19passportapp.Models.Passport;
import com.example.covid19passportapp.Models.Test;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repository {


    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://covid19passportapp-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference rootRef;
    private static Repository instance;

    private MutableLiveData<List<Test>> tests;
    private Passport passport;

    private Repository() {
        tests = new MutableLiveData<>();
        List<Test> mockUpTests = new ArrayList<Test>();
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "UNKNOWN"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "POSITIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        mockUpTests.add(new Test(DateTime.now(), "NEGATIVE"));
        tests.setValue(mockUpTests);

        database.setLogLevel(Logger.Level.DEBUG);
        //database.setPersistenceEnabled(true);
        rootRef = database.getReference();
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
        Citizen newCitizen = new Citizen();
        newCitizen.setFullName(citizen.getFullName());

        citizen.setBirthdateMilis(citizen.getBirthdate().getMillis());
        /*database.goOffline();
        database.goOnline();*/



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference db = rootRef.child("citizens").child(user.getUid());

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Citizen c = snapshot.getValue(Citizen.class);
                c.setBirthdate(new DateTime(c.getBirthdateMilis()));
                Log.d("FIREBASE_DATA_VALUE", c.getBirthdate().toString("dd/MM/yyyy"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db.setValue(citizen, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Log.d("FIREBASE", "success");
                } else {
                    Log.d("FIREBASE", error.toString());
                }
            }
        });



    }
}
