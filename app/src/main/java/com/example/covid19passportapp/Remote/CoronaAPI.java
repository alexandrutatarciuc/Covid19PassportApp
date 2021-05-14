package com.example.covid19passportapp.Remote;

import com.example.covid19passportapp.Models.CovidData;
import com.example.covid19passportapp.Models.CovidHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoronaAPI {

    @Headers({
            "x-rapidapi-key: 761a9a5be4msh04247509f6d9d97p1cc1bejsn3715e4f05dd0",
            "x-rapidapi-host: coronavirus-smartable.p.rapidapi.com"
    })
    @GET("stats/v1/{country}/")
    Call<CovidHistory> getLatestCountryDataByName(@Path("country") String country);
}
