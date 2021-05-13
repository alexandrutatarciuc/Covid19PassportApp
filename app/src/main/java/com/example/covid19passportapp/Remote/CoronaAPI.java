package com.example.covid19passportapp.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CoronaAPI {

    @Headers({
            "x-rapidapi-key: 761a9a5be4msh04247509f6d9d97p1cc1bejsn3715e4f05dd0",
            "x-rapidapi-host: covid-19-data.p.rapidapi.com"
    })
    @GET("country")
    Call<String> getLatestCountryDataByName(@Query("name") String country);
}
