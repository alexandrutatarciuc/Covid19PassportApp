package com.example.covid19passportapp.View;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covid19passportapp.Models.CovidData;
import com.example.covid19passportapp.R;
import com.example.covid19passportapp.ViewModel.StatisticsViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class StatisticsFragment extends Fragment {

    private LineChart graph;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        final Observer<List<CovidData>> allCovidDataObserver = measurements -> {
            // update graph
            LineData newLineData = getConfirmedCovidLineData(measurements);
            graph.setData(newLineData);
            graph.invalidate(); //refreshes graph
        };

        StatisticsViewModel statisticsViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        statisticsViewModel.receiveCovidData("DK");

        graph = view.findViewById(R.id.graph);

        LineDataSet lineDataSet = new LineDataSet(null, "Temperature Measurements Set");
        LineData lineData = new LineData((ILineDataSet) lineDataSet);
        graph.setData(lineData);

        //Styling
        graph.setDrawGridBackground(false);
        graph.setDrawBorders(false);
        graph.setDescription(null);
        graph.getLegend().setEnabled(false);

        //XAxis
        XAxis xAxis = graph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelCount(7, true);


        xAxis.setValueFormatter(new XAxisValueFormatter());

        //Right YAxis - disabled
        YAxis yr = graph.getAxisRight();
        yr.setEnabled(false);
        yr.setDrawAxisLine(false);

        statisticsViewModel.getCovidData().observe(getViewLifecycleOwner(), allCovidDataObserver);

        return view;
    }

    private LineData getConfirmedCovidLineData(List<CovidData> history) {
        ArrayList<Entry> values = new ArrayList<>();

        for (CovidData c : history) {
            values.add(new Entry(c.getDate().getMillis(), (float) c.getConfirmed()));
        }

        LineDataSet set1 = new LineDataSet(values, "Temperature Measurements Set");
        set1.setLineWidth(4f);
        set1.setColor(Color.parseColor("#0F75FF"));
        set1.setDrawCircles(false);
        set1.setHighLightColor(Color.parseColor("#0F77FF"));
        set1.setHighlightLineWidth(2f);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setDrawValues(true);
        set1.setDrawFilled(true);
        Log.i("SDK", String.valueOf(Utils.getSDKInt()));
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_blue);
            set1.setFillDrawable(drawable);
        }
        else {
            set1.setFillColor(Color.BLACK);
        }

        return new LineData(set1);
    }

    private LineData getDeathsCovidLineData(List<CovidData> history) {
        ArrayList<Entry> values = new ArrayList<>();

        for (CovidData c : history) {
            values.add(new Entry(c.getDate().getMillis(), (float) c.getDeaths()));
        }

        LineDataSet set1 = new LineDataSet(values, "Temperature Measurements Set");
        set1.setLineWidth(4f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(Color.parseColor("#4B6C53"));
        set1.setCircleColor(Color.WHITE);
        set1.setCircleHoleColor(Color.parseColor("#4B6C53"));
        set1.setHighLightColor(Color.parseColor("#48B864"));
        set1.setHighlightLineWidth(2f);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawValues(true);

        return new LineData(set1);
    }

    private LineData getRecoveredCovidLineData(List<CovidData> history) {
        ArrayList<Entry> values = new ArrayList<>();

        for (CovidData c : history) {
            values.add(new Entry(c.getDate().getMillis(), (float) c.getRecovered()));
        }

        LineDataSet set1 = new LineDataSet(values, "Temperature Measurements Set");
        set1.setLineWidth(4f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(Color.parseColor("#4B6C53"));
        set1.setCircleColor(Color.WHITE);
        set1.setCircleHoleColor(Color.parseColor("#4B6C53"));
        set1.setHighLightColor(Color.parseColor("#48B864"));
        set1.setHighlightLineWidth(2f);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawValues(true);

        return new LineData(set1);
    }
}