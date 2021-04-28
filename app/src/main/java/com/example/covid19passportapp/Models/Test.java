package com.example.covid19passportapp.Models;

import org.joda.time.DateTime;

public class Test {

    private int ID;
    private DateTime date;
    private String result;

    public Test() {
    }

    public Test(int ID, DateTime date, String result) {
        this.ID = ID;
        this.date = date;
        this.result = result;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
