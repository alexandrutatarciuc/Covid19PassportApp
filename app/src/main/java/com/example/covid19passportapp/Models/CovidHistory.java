package com.example.covid19passportapp.Models;

import android.util.Log;

import com.example.covid19passportapp.Remote.CustomCovidHistoryDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CovidHistory {
    private List<CovidData> history;

    public CovidHistory() {
        history = new ArrayList<>();
    }

    public List<CovidData> getHistory() {
        return history;
    }

    public void setHistory(List<CovidData> history) {
        this.history = history;
    }

    @JsonProperty("stats")
    public void unpackData(Map<String, Object> stats) throws IOException {
        List<String> jsonHistory = (List<String>) stats.get("history");


        Log.d("DESERIALIZE", jsonHistory.toString());
        ObjectMapper mapper = new ObjectMapper();

        /*List<CovidData> asList = mapper.readValue(
                jsonHistory, new TypeReference<List<CovidData>>() { });

        Log.d("DESERIALIZE", asList.get(100).toString());*/
    }
}
