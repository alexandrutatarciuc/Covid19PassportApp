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

    private static FirebaseUser currentUser;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance("https://covid19passportapp-default-rtdb.europe-west1.firebasedatabase.app/");
    private static DatabaseReference rootRef;

    private MutableLiveData<Citizen> citizen;
    private static MutableLiveData<Passport> passport;
    private static MutableLiveData<List<Test>> tests;
    private static MutableLiveData<String> fullName;
    private static MutableLiveData<DateTime> birthdate;
    private static ChildEventListener childEventListener;

    private static MutableLiveData<Boolean> isPassportCreated;

    private Repository() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        citizen = new MutableLiveData<>();
        passport = new MutableLiveData<>();
        fullName = new MutableLiveData<>();
        birthdate = new MutableLiveData<>();
        tests = new MutableLiveData<>();
        isPassportCreated = new MutableLiveData<>();
        //tests.setValue(new ArrayList<Test>()); //TODO Test remove

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //TODO maybe needs to be handled to set MutableLiveData for tests
                String dataModified = snapshot.getKey();

                switch (dataModified) {
                    case "fullName":
                        fullName.setValue((String) snapshot.getValue());
                        break;
                    case "birthdateMilis":
                        birthdate.setValue(new DateTime(snapshot.getValue()));
                        break;
                    case "passport":
                        Log.d("FIREBASE_PASSPORT", snapshot.getValue().toString());
                        Passport receivedPassport = snapshot.getValue(Passport.class);
                        //Converting millis to DateTime
                        receivedPassport.setVaccinationDate(new DateTime(receivedPassport.getVaccinationDateMilis()));
                        receivedPassport.setImmuneUntil(new DateTime(receivedPassport.getImmuneUntilMilis()));
                        passport.setValue(receivedPassport);
                        isPassportCreated.setValue(true);
                        break;
                    case "tests":
                        List<Test> receivedTests = new ArrayList<>();
                        //Looping through snapshot's children and saving them as separate tests
                        for (DataSnapshot d : snapshot.getChildren()) {
                            Test test = d.getValue(Test.class);
                            receivedTests.add(test);
                        }
                        //Converting millis to DateTime
                        for (Test t : receivedTests) {
                            t.setDate(new DateTime(t.getDateMilis()));
                        }
                        tests.setValue(receivedTests);
                        break;
                    default:
                        Log.wtf("REPOSITORY", "Data not recognized");
                        break;
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String dataModified = snapshot.getKey();

                switch (dataModified) {
                    case "fullName":
                        fullName.setValue((String) snapshot.getValue());
                        break;
                    case "birthdateMilis":
                        birthdate.setValue(new DateTime(snapshot.getValue()));
                        break;
                    case "passport":
                        Log.d("FIREBASE_PASSPORT", snapshot.getValue().toString());
                        Passport receivedPassport = snapshot.getValue(Passport.class);
                        //Converting millis to DateTime
                        receivedPassport.setVaccinationDate(new DateTime(receivedPassport.getVaccinationDateMilis()));
                        receivedPassport.setImmuneUntil(new DateTime(receivedPassport.getImmuneUntilMilis()));
                        passport.setValue(receivedPassport);
                        isPassportCreated.setValue(true);
                        break;
                    case "tests":
                        List<Test> receivedTests = new ArrayList<>();
                        //Looping through snapshot's children and saving them as separate tests
                        for (DataSnapshot d : snapshot.getChildren()) {
                            Test test = d.getValue(Test.class);
                            receivedTests.add(test);
                        }
                        //Converting millis to DateTime
                        for (Test t : receivedTests) {
                            t.setDate(new DateTime(t.getDateMilis()));
                        }
                        tests.setValue(receivedTests);
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
        };

        database.setLogLevel(Logger.Level.DEBUG);   //Debugging Firebase
        database.setPersistenceEnabled(true);   //Enabling Offline Persistence
        rootRef = database.getReference();

        if (currentUser != null) {
            listenToCitizensDataUpdates();
        }
    }

    public static synchronized Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    public static void destroyCurrentUser() {
        DatabaseReference db = rootRef.child("citizens").child(currentUser.getUid());
        db.removeEventListener(childEventListener);
        currentUser = null;
        fullName = new MutableLiveData<>();
        birthdate = new MutableLiveData<>();
        passport = new MutableLiveData<>();
        tests = new MutableLiveData<>();
        isPassportCreated = new MutableLiveData<>();
    }

    public static void updateCurrentUser() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference db = rootRef.child("citizens").child(currentUser.getUid());
        db.addChildEventListener(childEventListener);
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
        //Objects.requireNonNull(tests.getValue()).add(test);
        test.setDateMilis(test.getDate().getMillis());

        //Reference to the current user
        DatabaseReference db = rootRef.child("citizens").child(currentUser.getUid()).child("tests").push();

        //Adding to the Firebase Realtime Database
        db.setValue(test, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Log.d("FIREBASE", "Test added successfully");
                } else {
                    Log.d("FIREBASE", error.toString());
                }
            }
        });
    }

    public LiveData<Passport> getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        passport.setVaccinationDateMilis(passport.getVaccinationDate().getMillis());
        passport.setImmuneUntilMilis(passport.getImmuneUntil().getMillis());

        //Reference to the current user
        DatabaseReference db = rootRef.child("citizens").child(currentUser.getUid()).child("passport");

        //Adding to the Firebase Realtime Database
        db.setValue(passport, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Log.d("FIREBASE", "Passport added successfully");
                    isPassportCreated.setValue(true);
                } else {
                    Log.d("FIREBASE", error.toString());
                }
            }
        });
    }

    public LiveData<Boolean> isPassportCreated() {
        return isPassportCreated;
    }

    public LiveData<Citizen> getCitizen() {
        return citizen;
    }

    public void addCitizen(Citizen citizen, String uid) {
        //Setting DateTime birthdate
        citizen.setBirthdateMilis(citizen.getBirthdate().getMillis());

        //Reference to the current user
        DatabaseReference db = rootRef.child("citizens").child(uid);

        //Adding to the Firebase Realtime Database
        db.setValue(citizen, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    Log.d("FIREBASE", "Citizen added successfully");
                    listenToCitizensDataUpdates();
                } else {
                    Log.d("FIREBASE", error.toString());
                }
            }
        });
    }

    private void listenToCitizensDataUpdates() {
        DatabaseReference db = rootRef.child("citizens").child(currentUser.getUid());
        db.addChildEventListener(childEventListener);
    }
}
