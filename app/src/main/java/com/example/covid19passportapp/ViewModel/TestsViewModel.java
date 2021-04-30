package com.example.covid19passportapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid19passportapp.Models.Test;
import com.example.covid19passportapp.Persistence.Repository;

import java.util.List;

public class TestsViewModel extends ViewModel {

    private Repository repository;

    public TestsViewModel() {
        repository = Repository.getInstance();
    }

    public LiveData<List<Test>> getAllTests() {
        return repository.getTests();
    }

    public void addTest(Test test) {
        repository.addTest(test);
    }
}
