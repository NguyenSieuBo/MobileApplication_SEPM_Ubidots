package com.ubidots.bo.ubidotsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListViewActivity extends AppCompatActivity {
    private TextView welcome;
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ListViewActivity", "Now running onStart");
    }

    @Override
    protected void onResume() {
        Log.i("ListViewActivity", "Now running onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("ListViewActivity", "Now running onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i("ListViewActivity", "Now running onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.i("ListViewActivity", "Now running onRestart");
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("ListViewActivity", "Now running onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        String greeting ="Welcome to Smart Plant System Application - SEPM" + "\n"
                + "Here are some options:";
        welcome = (TextView) findViewById(R.id.txtusername);
        welcome.setText(greeting);
        List<ViewOptions> image_details = getListData();
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomListAdapter(this, image_details));


        // ClickListener enable when user click item of the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                TextView t1  = (TextView) v.findViewById(R.id.itemView);

                if (Objects.equals(t1.getText().toString(), "Temperature Chart")) {
                    Intent intent1 = new Intent(ListViewActivity.this, TemperatureChartActivity.class);
                    startActivity(intent1);
                } else if (Objects.equals(t1.getText().toString(), "Humidity Chart")){
                    Intent intent1 = new Intent(ListViewActivity.this, HumidityChartActivity.class);
                    startActivity(intent1);
                }else if (Objects.equals(t1.getText().toString(), "Soil Humidity Chart")) {
                    Intent intent1 = new Intent(ListViewActivity.this, SoilMoistureChartActivity.class);
                    startActivity(intent1);
                }

            }
        });

    }
    private  List<ViewOptions> getListData() {
        List<ViewOptions> list = new ArrayList<ViewOptions>();
        ViewOptions temp = new ViewOptions("Temperature Chart", "temp", "Line Chart");
        ViewOptions hum = new ViewOptions("Humidity Chart", "hum", "Bar Chart");
        ViewOptions soilHum = new ViewOptions("Soil Humidity Chart", "soilhum", "Bar Chart");
        list.add(temp);
        list.add(hum);
        list.add(soilHum);
        return list;
    }


}
