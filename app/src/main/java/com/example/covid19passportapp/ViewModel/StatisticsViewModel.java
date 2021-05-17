package com.example.covid19passportapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid19passportapp.Models.CovidData;
import com.example.covid19passportapp.Models.Test;
import com.example.covid19passportapp.Persistence.Repository;

import java.util.ArrayList;
import java.util.List;

public class StatisticsViewModel extends ViewModel {

    private Repository repository;

    public StatisticsViewModel() {
        repository = Repository.getInstance();
    }

    public LiveData<ArrayList<CovidData>> getCovidData() {
        return repository.getCovidData();
    }

    public void receiveCovidData(String country) {
        repository.receiveCovidData(country);
    }
}
