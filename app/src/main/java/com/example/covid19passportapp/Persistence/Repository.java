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
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Repository {

    private static Repository instance;

    private final FirebaseUser currentUser;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://covid19passportapp-default-rtdb.europe-west1.firebasedatabase.app/");
    private final DatabaseReference rootRef;

    private MutableLiveData<Citizen> citizen;
    private MutableLiveData<Passport> passport;
    private MutableLiveData<List<Test>> tests;
    private MutableLiveData<String> fullName;
    private MutableLiveData<DateTime> birthdate;

    private Repository() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        citizen = new MutableLiveData<>();
        passport = new MutableLiveData<>();
        fullName = new MutableLiveData<>();
        birthdate = new MutableLiveData<>();
        tests = new MutableLiveData<>();
        /*List<Test> mockUpTests = new ArrayList<Test>();
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
        tests.setValue(mockUpTests);*/

        database.setLogLevel(Logger.Level.DEBUG);   //Debugging Firebase
        database.setPersistenceEnabled(true);   //Enabling Offline Persistence
        rootRef = database.getReference();

        listenToCitizensDataUpdates();
    }

    public static synchronized Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    public LiveData<String> getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.setValue(fullName);
    }

    public LiveData<DateTime> getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(DateTime birthdate) {
        this.birthdate.setValue(birthdate);
    }

    public LiveData<List<Test>> getTests() {
        return tests;
    }

    public void addTest(Test test) {
        Objects.requireNonNull(tests.getValue()).add(test);
    }

    public LiveData<Passport> getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport.setValue(passport);
    }

    public boolean isPassportCreated() {
        return passport.getValue() != null;
    }

    public LiveData<Citizen> getCitizen() {
        return citizen;
    }

    public void addCitizen(Citizen citizen) {
        //Setting DateTime birthdate
        citizen.setBirthdateMilis(citizen.getBirthdate().getMillis());

        //Updating fullName and birthdate
        fullName.setValue(citizen.getFullName());
        birthdate.setValue(citizen.getBirthdate());

        //Reference to the current user
        DatabaseReference db = rootRef.child("citizens").child(currentUser.getUid());

        //Adding to the Firebase Realtime Database
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

        //Updating Livedata
        this.citizen.setValue(citizen);
    }

    private void listenToCitizensDataUpdates() {
        //Reference to the current user
        DatabaseReference db = rootRef.child("citizens").child(currentUser.getUid());

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //TODO maybe needs to be handled to set MutableLiveData for tests
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String dataModified = snapshot.getKey();

                switch (dataModified) {
                    case "fullName":
                        fullName.setValue((String) snapshot.getValue());
                        break;
                    case "birthdate":
                        birthdate.setValue((DateTime) snapshot.getValue());
                        break;
                    case "passport":
                        passport.setValue((Passport) snapshot.getValue());
                        break;
                    case "tests":
                        tests.setValue((List<Test>) snapshot.getValue());
                        break;
                    default:
                        Log.wtf("REPOSITORY", "Data not recognized");
                        break;
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
