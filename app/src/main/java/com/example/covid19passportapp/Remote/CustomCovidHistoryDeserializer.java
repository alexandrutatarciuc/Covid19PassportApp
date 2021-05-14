package com.example.covid19passportapp.Remote;

import android.util.Log;

import com.example.covid19passportapp.Models.CovidData;
import com.example.covid19passportapp.Models.CovidHistory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomCovidHistoryDeserializer extends StdDeserializer<CovidHistory> {

    public CustomCovidHistoryDeserializer() {
        this(null);
    }

    public CustomCovidHistoryDeserializer(Class<?> vc) {
        super(vc);
    }


    @Override
    public CovidHistory deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);
        CovidHistory covidHistory = new CovidHistory();
        List<CovidData> list = new ArrayList<>();
        Log.d("DESERIALIZATION", node.asText());
        if (p.getCurrentToken() == JsonToken.START_ARRAY) {
            while (p.nextToken() != JsonToken.END_ARRAY) {
                try {
                    JsonNode dateNode = node.get("date");
                    DateTime dateTime = DateTime.parse(dateNode.asText());
                    JsonNode confirmedNode = node.get("confirmed");
                    long confirmed = confirmedNode.asLong();
                    JsonNode deathsNode = node.get("deaths");
                    long deaths = deathsNode.asLong();
                    JsonNode recoveredNode = node.get("recovered");
                    long recovered = recoveredNode.asLong();
                    list.add(new CovidData(dateTime, confirmed, deaths, recovered));
                } catch (Exception e) {
                    Log.d("DESERIALIZATION", e.getMessage());
                }
            }
            covidHistory.setHistory(list);
            return covidHistory;
        }
        throw ctxt.mappingException("Expected Permissions list");
    }
}
