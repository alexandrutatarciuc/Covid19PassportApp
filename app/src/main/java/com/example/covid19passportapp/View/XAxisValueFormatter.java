package com.example.covid19passportapp.View;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class XAxisValueFormatter extends ValueFormatter {


    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        /*axis.setLabelCount(3, true);*/
        return convertFloatToDate(value);

    }

    private String convertFloatToDate(float value)
    {
        DateTime dateTime = new DateTime((long) value);
        return dateTime.toString("MMM d");
    }
}