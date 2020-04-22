package com.example.assignment1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class Setting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView temp_unit, pres_unit, wind_unit;
    Button get_back;
    Spinner temp, pres, wind_st;
    String t_unit, p_unit, w_unit;
    String[] temp_units = {"\u2103", "\u2109"};
    String[] pres_units = {"hPa", "atm"};
    String[] wind_units = {"m/s", "mph"};
    int t_position, p_position, w_position;
    public static final String PRES_NAME = "MyPresFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        get_back =findViewById(R.id.back);

        temp = findViewById(R.id.temp);
        pres = findViewById(R.id.pres);
        wind_st = findViewById(R.id.wind_st);

        wind_unit = findViewById(R.id.wind_unit);
        pres_unit = findViewById(R.id.pres_unit);
        temp_unit = findViewById(R.id.temp_unit);

        get_back.setTextColor(Color.BLACK);

        final SharedPreferences spinnersaving = getSharedPreferences(PRES_NAME,0);

        temp.setSelection(spinnersaving.getInt("tempPosition", 0));
        pres.setSelection(spinnersaving.getInt("presPosition", 0));
        wind_st.setSelection(spinnersaving.getInt("windPosition", 0));


        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Setting.this,android.R.layout.simple_spinner_item,temp_units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temp.setAdapter(adapter);


        ArrayAdapter<String>adapter1 = new ArrayAdapter<String>(Setting.this,android.R.layout.simple_spinner_item,pres_units);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pres.setAdapter(adapter1);

        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(Setting.this,android.R.layout.simple_spinner_item,wind_units);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wind_st.setAdapter(adapter2);

        temp.setOnItemSelectedListener(this);
        pres.setOnItemSelectedListener(this);
        wind_st.setOnItemSelectedListener(this);

//        temp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                t_position = position;
//                t_unit = (String) temp.getItemAtPosition(t_position);
//                temp_unit.setText(t_unit);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        pres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                p_position = position;
//                p_unit = (String) pres.getItemAtPosition(p_position);
//                pres_unit.setText(p_unit);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        wind_st.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                w_position = position;
//                w_unit = (String) wind_st.getItemAtPosition(w_position);
//                wind_unit.setText(w_unit);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        get_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Setting.this, MainActivity.class);
//                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent2.putExtra("value", new String[]{t_unit, p_unit, w_unit});
                startActivity(intent2);

            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences spinnersaving = getSharedPreferences(PRES_NAME,0);

        SharedPreferences.Editor editor = spinnersaving.edit();
        editor.putInt("tempPosition", t_position);
        editor.putInt("presPosition", p_position);
        editor.putInt("windPosition", w_position);

        editor.apply();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int pos,long id) {
        int check = 0;
        if(++check > 1) {
            if (parent == temp) {
                t_position = pos;
                t_unit = (String) temp.getItemAtPosition(t_position);
                temp_unit.setText(t_unit);
            } else if (parent == pres) {
                p_position = pos;
                p_unit = (String) pres.getItemAtPosition(p_position);
                pres_unit.setText(p_unit);
            } else {
                w_position = pos;
                w_unit = (String) wind_st.getItemAtPosition(w_position);
                wind_unit.setText(w_unit);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
