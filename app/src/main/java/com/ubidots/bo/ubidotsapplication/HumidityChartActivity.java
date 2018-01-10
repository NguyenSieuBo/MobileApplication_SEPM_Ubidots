package com.ubidots.bo.ubidotsapplication;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HumidityChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidity_chart);
        FragmentTransaction tra = getSupportFragmentManager().beginTransaction();
        tra.add(R.id.frgmt1, new HumFragment());
        tra.commit();
    }
}
