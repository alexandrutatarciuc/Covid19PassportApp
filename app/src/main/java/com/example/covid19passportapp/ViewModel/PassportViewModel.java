package com.example.covid19passportapp.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.covid19passportapp.Models.Passport;
import com.example.covid19passportapp.Persistence.Repository;

public class PassportViewModel extends ViewModel {

    private Repository repository;

    public PassportViewModel() {
        repository = Repository.getInstance();
    }

    public LiveData<Passport> getPassport() {
        return repository.getPassport();
    }

    public void setPassport(Passport passport) {
        repository.setPassport(passport);
    }

    public LiveData<Boolean> isPassportCreated() {
        return repository.isPassportCreated();
    }

    public String getQrCodeUrl(Passport passport) {
        return "https://api.qrserver.com/v1/create-qr-code/?data=" + passport.getId();
    }
}
