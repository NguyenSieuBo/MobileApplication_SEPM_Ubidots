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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Bo on 12/12/17.
 */

public class SoilHumFragment extends Fragment {
    private String API_KEY = "A1E-dmKoBrKamDEtABtu2hSnteIC1G2xU9";
    private String soilHumVarId = "5a1fcc9f76254228d479f1c5";
    private long millis;
    private float totalSoilHumValue, currentSoilHumValue, meanSoilHumValue;
    private String currentSoilHum1, meanSoilHum1;
    TextView currentSoilHum, meanSoilHum;
    private ArrayList<Float> valuesSoilHum = new ArrayList<Float>();


    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final String strDate ="12/24/2017";
    private BarChart soilHumChart;

    public SoilHumFragment() {  }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.soilhum_fragment, container, false);
        soilHumChart = (BarChart) v.findViewById(R.id.chartSoilHum);
        currentSoilHum = (TextView) v.findViewById(R.id.currentSoilHum);
        meanSoilHum = (TextView) v.findViewById(R.id.meanSoilHum);
        initChartBar(soilHumChart);
        try {
            millis = new SimpleDateFormat("MM/dd/yyyy").parse(strDate).getTime();
        } catch (ParseException e){
            e.printStackTrace();
        }
        // Humidity
        ( new UbidotsClient() ).handleUbidots(soilHumVarId, API_KEY, millis, new UbidotsClient.UbiListener() {
            @Override
            public void onDataReady(List<UbidotsClient.Value> result) {
                Log.d("Chart", "======== On data Ready ===========");
                List<BarEntry> entries = new ArrayList();
                List<String> labels = new ArrayList<String>();
                for (int i=0; i < result.size(); i++) {

                    BarEntry be = new BarEntry(result.get(i).value, i);
                    valuesSoilHum.add(result.get(i).value);
                    totalSoilHumValue += result.get(i).value;
                    entries.add(be);
                    Log.d("Chart", be.toString());
                    // Convert timestamp to date
                    Date d = new Date(result.get(i).timestamp);
                    // Create Labels
                    labels.add(sdf.format(d));
                }
                calculate();
                BarDataSet lse = new BarDataSet(entries, "Soil Humidity");

                lse.setDrawValues(false);
                lse.setColor(Color.DKGRAY);

                BarData bd = new BarData(labels, lse);

                soilHumChart.setData(bd);
                Handler handler = new Handler(SoilHumFragment.this.getActivity().getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        soilHumChart.invalidate();
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


    private void initChartBar(BarChart chart) {
        chart.setTouchEnabled(true);
        chart.setDrawGridBackground(true);
        chart.getAxisRight().setEnabled(false);
        chart.setDrawGridBackground(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMaxValue(100F);
        leftAxis.setAxisMinValue(0F);
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
                meanSoilHumValue = totalSoilHumValue / valuesSoilHum.size();
                meanSoilHum1 = String.valueOf(meanSoilHumValue);
                meanSoilHum.setText(meanSoilHum1);
                currentSoilHumValue = valuesSoilHum.get(valuesSoilHum.size() - 1);
                currentSoilHum1 = String.valueOf(currentSoilHumValue);
                currentSoilHum.setText(currentSoilHum1);
            }
        });
    }
}
