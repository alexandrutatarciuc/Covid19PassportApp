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
            "x-rapidapi-key: 3df158567bmsh3170cdc85b49adfp1cb11ejsne28c098375fb",
            "x-rapidapi-host: coronavirus-smartable.p.rapidapi.com"
    })
    @GET("stats/v1/{country}/")
    Call<CovidHistory> getLatestCountryDataByName(@Path("country") String country);
}
