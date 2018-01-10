package com.ubidots.bo.ubidotsapplication;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SoilMoistureChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soil_moisture);
        FragmentTransaction tra = getSupportFragmentManager().beginTransaction();
        tra.add(R.id.frgmt2, new SoilHumFragment());
        tra.commit();
    }
}
