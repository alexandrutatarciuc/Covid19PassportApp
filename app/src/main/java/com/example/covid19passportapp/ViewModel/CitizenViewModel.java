package com.example.covid19passportapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid19passportapp.Models.Citizen;
import com.example.covid19passportapp.Persistence.Repository;

import org.joda.time.DateTime;

public class CitizenViewModel extends ViewModel {

    private Repository repository;

    public CitizenViewModel() {
        repository = Repository.getInstance();
    }


    public void addCitizen(Citizen citizen) {
        repository.addCitizen(citizen);
        Log.d("CITIZEN_VIEW_MODEL","I GOT HERE");
    }

    public LiveData<String> getFullName() {
        return repository.getFullName();
    }

    public LiveData<DateTime> getBirthdate() {
        return repository.getBirthdate();
    }

}
