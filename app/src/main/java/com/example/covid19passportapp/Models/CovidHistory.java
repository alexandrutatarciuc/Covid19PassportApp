package com.example.covid19passportapp.Models;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        ArrayList<?> jsonHistory = (ArrayList<?>) stats.get("history");
        Log.d("DESERIALIZE", stats.get("history").toString());

        for (Object o : jsonHistory) {
            try {
                LinkedHashMap<String, Object> newO = (LinkedHashMap<String, Object>) o;
                CovidData data = new CovidData(DateTime.parse((String) newO.get("date")), (int) newO.get("confirmed"), (int) newO.get("deaths"), (int) newO.get("recovered"));
                history.add(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
