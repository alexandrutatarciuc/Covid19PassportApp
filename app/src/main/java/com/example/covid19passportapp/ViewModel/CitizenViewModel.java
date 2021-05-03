package com.example.covid19passportapp.ViewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.covid19passportapp.Models.Citizen;
import com.example.covid19passportapp.Persistence.Repository;

public class CitizenViewModel extends ViewModel {

    private Repository repository;

    public CitizenViewModel() {
        repository = Repository.getInstance();
    }

    public boolean createCitizen() {
        return false;
    }

    public void addCitizen(Citizen citizen) {
        repository.addCitizen(citizen);
        Log.d("CITIZEN_VIEW_MODEL","I GOT HERE");
    }

}
