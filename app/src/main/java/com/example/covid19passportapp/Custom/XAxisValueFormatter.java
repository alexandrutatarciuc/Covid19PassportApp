package com.example.covid19passportapp.Custom;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XAxisValueFormatter extends ValueFormatter {


    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        DateTime dateTime = new DateTime((long) value);
        return dateTime.toString("MMM d");
    }
}