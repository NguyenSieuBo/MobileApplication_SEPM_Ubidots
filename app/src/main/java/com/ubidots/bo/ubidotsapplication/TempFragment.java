package com.ubidots.bo.ubidotsapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Bo on 12/8/17.
 */

public class TempFragment extends Fragment {
    private String API_KEY = "A1E-dmKoBrKamDEtABtu2hSnteIC1G2xU9";
    private String tempVarId = "5a1fcc9e76254228d479f1ab";
    private long millis;
    private float totalTempValue, currentTempValue, meanTempValue;
    private String currentTemp1, meanTemp1;
    TextView currentTemp, meanTemp;
    private ArrayList<Float> valuesTemp = new ArrayList<Float>();

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final String strDate = "12/24/2017";

    // UI reference
    private LineChart tempChart;

    public TempFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.temp_fragment, container, false);
        tempChart = (LineChart) v.findViewById(R.id.chartTemp);
        currentTemp = (TextView) v.findViewById(R.id.currentTemp);
        meanTemp = (TextView) v.findViewById(R.id.meanTemp);
        initChartTemp(tempChart);
        try {
            millis = new SimpleDateFormat("MM/dd/yyyy").parse(strDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        (new UbidotsClient()).handleUbidots(tempVarId, API_KEY, millis, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value> result) {
                Log.d("Chart", "======== On data Ready ===========");
                List<Entry> entries = new ArrayList();
                List<String> labels = new ArrayList<String>();
                for (int i = 0; i < result.size(); i++) {
                    Entry be = new Entry(result.get(i).value, i);
                    valuesTemp.add(result.get(i).value);
                    totalTempValue += result.get(i).value;
                    entries.add(be);
                    Log.d("Chart", be.toString());
                    // Convert timestamp to date
                    Date d = new Date(result.get(i).timestamp);
                    // Create Labels
                    labels.add(sdf.format(d));
                }
                calculate();
                LineDataSet lse = new LineDataSet(entries, "Temperature");

                lse.setDrawHighlightIndicators(false);
                lse.setDrawValues(false);
                lse.setColor(Color.RED);
                lse.setCircleColor(Color.RED);
                lse.setLineWidth(1f);
                lse.setCircleSize(3f);
                lse.setDrawCircleHole(false);
                lse.setFillAlpha(65);
                lse.setFillColor(Color.RED);

                LineData ld = new LineData(labels, lse);

                tempChart.setData(ld);
                Handler handler = new Handler(TempFragment.this.getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tempChart.invalidate();
                    }
                });

            }
        });
        return v;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void initChartTemp(LineChart chart) {
        chart.setTouchEnabled(true);
        chart.setDrawGridBackground(true);
        chart.getAxisRight().setEnabled(false);
        chart.setDrawGridBackground(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaxValue(30F);
        leftAxis.setAxisMinValue(10F);
        leftAxis.setStartAtZero(false);
        leftAxis.setAxisLineWidth(2);
        leftAxis.setDrawGridLines(true);

        // X-Axis
        XAxis xAxis = chart.getXAxis();
        xAxis.resetLabelsToSkip();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
    }
    public void calculate() {
        // Your logic here...

        // When you need to modify a UI element, do so on the UI thread.
        // 'getActivity()' is required as this is being ran from a Fragment.
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                meanTempValue = totalTempValue / valuesTemp.size();
                meanTemp1 = String.valueOf(meanTempValue);
                meanTemp.setText(meanTemp1);
                currentTempValue = valuesTemp.get(valuesTemp.size() - 1);
                currentTemp1 = String.valueOf(currentTempValue);
                currentTemp.setText(currentTemp1);

            }
        });
    }

}
