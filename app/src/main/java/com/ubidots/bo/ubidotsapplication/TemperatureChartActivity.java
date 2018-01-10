package com.ubidots.bo.ubidotsapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class TemperatureChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_chart);

        FragmentTransaction tra = getSupportFragmentManager().beginTransaction();
        tra.add(R.id.frgmt, new TempFragment());
        tra.commit();
    }


}
