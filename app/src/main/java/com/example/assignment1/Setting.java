package com.example.assignment1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    private SharedPreferences preferences;
    int check = 0;

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

        preferences = getSharedPreferences("value", MODE_PRIVATE);


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


        get_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Setting.this, MainActivity.class);
//                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent2.putExtra("value", new String[]{t_unit, p_unit, w_unit});
                setResult(Setting.RESULT_OK, intent2);
                finish();
            }

        });
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onStop() {
        super.onStop();

        preferences.edit()
                .putInt("tempPosition", t_position)
                .putInt("presPosition", p_position)
                .putInt("windPosition", w_position)
                .apply();

    }

    @Override
    protected void onStart() {
        super.onStart();

        t_position = preferences.getInt("tempPosition", 0);
        p_position = preferences.getInt("presPosition", 0);
        w_position = preferences.getInt("windPosition", 0);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int pos,long id) {
        check += 1;
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
