package com.example.covid19passportapp.Remote;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CoronaServiceGenerator {

    private static final String BASE_URL = "https://coronavirus-smartable.p.rapidapi.com/";
    private static CoronaAPI coronaAPI;

    public static CoronaAPI getCoronaAPI()
    {
        if (coronaAPI == null)
        {
            coronaAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()
                    .create(CoronaAPI.class);
        }
        return coronaAPI;
    }

}
