package com.example.covid19passportapp.Remote;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CoronaServiceGenerator {

    private static final String BASE_URL = "https://covid-19-data.p.rapidapi.com/";
    private static final String BASE_URL_2 = "https://coronavirus-monitor.p.rapidapi.com/";
    private static CoronaAPI coronaAPI;

    public static CoronaAPI getCoronaAPI()
    {
        if (coronaAPI == null)
        {
            /*OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("x-rapidapi-key", "761a9a5be4msh04247509f6d9d97p1cc1bejsn3715e4f05dd0")
                            .header("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });
            OkHttpClient client = httpClient.build();*/


            coronaAPI = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    /*.client(client)*/
                    .build()
                    .create(CoronaAPI.class);
        }
        return coronaAPI;
    }

}
