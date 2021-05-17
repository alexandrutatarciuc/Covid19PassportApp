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
import android.widget.Button;

import com.example.covid19passportapp.Custom.CustomMarkerView;
import com.example.covid19passportapp.Custom.XAxisValueFormatter;
import com.example.covid19passportapp.Models.CovidData;
import com.example.covid19passportapp.R;
import com.example.covid19passportapp.ViewModel.StatisticsViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
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
    private Button dataType;
    private List<CovidData> localCovidData;

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

        final Observer<List<CovidData>> allCovidDataObserver = data -> {
            // update graph
            LineData newLineData = new LineData(getConfirmedCovidLineDataSet(data));
            graph.setData(newLineData);
            localCovidData = data;
            graph.invalidate(); //refreshes graph
        };

        StatisticsViewModel statisticsViewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        statisticsViewModel.receiveCovidData("DK");
        statisticsViewModel.getCovidData().observe(getViewLifecycleOwner(), allCovidDataObserver);

        graph = view.findViewById(R.id.graph);
        dataType = view.findViewById(R.id.dataInput);

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

        //Marker
        IMarker marker = new CustomMarkerView(getContext(), R.layout.graph_marker);
        graph.setMarker(marker);

        dataType.setOnClickListener(v -> {
            if (dataType.getText().toString().equalsIgnoreCase("confirmed")) {
                dataType.setText("Recovered");
                dataType.getBackground().setTint(Color.parseColor("#06EB68"));
                graph.clear();
                LineData newLineData = new LineData(getRecoveredCovidLineDataSet(localCovidData));
                graph.setData(newLineData);
                graph.invalidate();

            } else if (dataType.getText().toString().equalsIgnoreCase("recovered")) {
                dataType.setText("Deaths");
                dataType.getBackground().setTint(Color.parseColor("#FF0A00"));
                graph.clear();
                LineData newLineData = new LineData(getDeathsCovidLineDataSet(localCovidData));
                graph.setData(newLineData);
                graph.invalidate();

            } else if (dataType.getText().toString().equalsIgnoreCase("deaths")) {
                dataType.setText("Confirmed");
                dataType.getBackground().setTint(Color.parseColor("#0F77FF"));
                graph.clear();
                LineData newLineData = new LineData(getConfirmedCovidLineDataSet(localCovidData));
                graph.setData(newLineData);
                graph.invalidate();
            } else {
                Log.wtf("DATA_TYPE_BUTTON", "Unexpected button text");
            }
        });

        return view;
    }

    private LineDataSet getConfirmedCovidLineDataSet(List<CovidData> history) {
        ArrayList<Entry> values = new ArrayList<>();

        for (CovidData c : history) {
            values.add(new Entry(c.getDate().getMillis(), (float) c.getConfirmed()));
        }

        LineDataSet set1 = new LineDataSet(values, "Confirmed");
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
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_blue);
        set1.setFillDrawable(drawable);
        return set1;
    }

    private LineDataSet getRecoveredCovidLineDataSet(List<CovidData> history) {
        ArrayList<Entry> values = new ArrayList<>();

        for (CovidData c : history) {
            values.add(new Entry(c.getDate().getMillis(), (float) c.getRecovered()));
        }

        LineDataSet set1 = new LineDataSet(values, "Recovered");
        set1.setLineWidth(4f);
        set1.setColor(Color.parseColor("#06EB68"));
        set1.setDrawCircles(false);
        set1.setHighLightColor(Color.parseColor("#06EB68"));
        set1.setHighlightLineWidth(2f);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setDrawValues(true);
        set1.setDrawFilled(true);
        Log.i("SDK", String.valueOf(Utils.getSDKInt()));
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_green);
        set1.setFillDrawable(drawable);
        return set1;
    }

    private LineDataSet getDeathsCovidLineDataSet(List<CovidData> history) {
        ArrayList<Entry> values = new ArrayList<>();

        for (CovidData c : history) {
            values.add(new Entry(c.getDate().getMillis(), (float) c.getDeaths()));
        }

        LineDataSet set1 = new LineDataSet(values, "Deaths");
        set1.setLineWidth(4f);
        set1.setColor(Color.parseColor("#FF0A00"));
        set1.setDrawCircles(false);
        set1.setHighLightColor(Color.parseColor("#FF0A00"));
        set1.setHighlightLineWidth(2f);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setDrawValues(true);
        set1.setDrawFilled(true);
        Log.i("SDK", String.valueOf(Utils.getSDKInt()));
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
        set1.setFillDrawable(drawable);
        return set1;
    }

}