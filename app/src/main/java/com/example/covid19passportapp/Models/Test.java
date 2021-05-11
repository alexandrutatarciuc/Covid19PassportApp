package com.example.covid19passportapp.Models;

import com.google.firebase.database.Exclude;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Test {

    @Exclude
    private DateTime date;
    private String result;
    private long dateMilis;

    public Test() {
    }


    public Test(DateTime date, String result) {
        this.date = date;
        this.result = result;
    }

    @Exclude
    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getDateMilis() {
        return dateMilis;
    }

    public void setDateMilis(long dateMilis) {
        this.dateMilis = dateMilis;
    }
}
