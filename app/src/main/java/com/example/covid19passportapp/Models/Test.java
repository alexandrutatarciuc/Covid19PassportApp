package com.example.covid19passportapp.Models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Test {


    private DateTime date;
    private String result;

    public Test() {
    }

    public Test(int ID, DateTime date, String result) {
        this.date = date;
        this.result = result;
    }

    public Test(DateTime date, String result) {
        this.date = date;
        this.result = result;
    }

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
}
