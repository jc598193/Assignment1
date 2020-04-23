package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    TextView location, humidity, degree, status, time, wind, pressure;
    List<String> list_city = Arrays.asList("Singapore", "Hanoi", "Washington, D.C.", "London");
    Integer[] list_background = new Integer[]{R.drawable.freezy, R.drawable.frostynight, R.drawable.sunshine, R.drawable.nightsky, R.drawable.rainy, R.drawable.rainy_night};
    String degree_unit, humidity_unit, wind_unit, pressure_unit, updatedAtText, description, city;
    int wind_value, degree_value, humidity_value, pressure_value;
    Boolean rain;
    LinearLayout setting, main;

    public static final String CITY_NAME = "Cityname";
    RandomClass newran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        import RandomClass
        newran = new RandomClass();

        location = findViewById(R.id.location);
        humidity = findViewById(R.id.humidity);
        degree = findViewById(R.id.degree);
        status = findViewById(R.id.status);
        time = findViewById(R.id.time);
        wind = findViewById(R.id.wind);
        pressure = findViewById(R.id.pressure);
        setting = findViewById(R.id.setting);
        main = findViewById(R.id.main);

        city = list_city.get(newran.normal_rd(list_city.size()));

        // Instantiate the RequestQueue.
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&appid=d172e20b9b43178c9bd16fad14172e03";

        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject mainobj = obj.getJSONObject("main");
                            JSONObject wind_obj = obj.getJSONObject("wind");
                            JSONObject weather_obj = obj.getJSONArray("weather").getJSONObject(0);

                            Long updateAt = obj.getLong("dt");
                            updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(new Date(updateAt * 1000));
                            degree_value = (int) Float.parseFloat(mainobj.getString("temp"));
                            pressure_value = (int) Float.parseFloat(mainobj.getString("pressure"));
                            humidity_value = (int) Float.parseFloat(mainobj.getString("humidity"));
                            wind_value = (int) Float.parseFloat(wind_obj.getString("speed"));
                            description = weather_obj.getString("description");


                            location.setText(city);


                            degree_unit = "\u2103";                     //Celsius degree
                            degree.setText(degree_value + degree_unit);

                            humidity_unit = "%";
                            humidity.setText(humidity_value + humidity_unit);


                            time.setText(updatedAtText);


                            wind_unit = "m/s";
                            wind.setText(wind_value + wind_unit);


                            pressure_unit = "hPa";
                            pressure.setText(pressure_value + pressure_unit);
                            status.setText(description);
                            // Set background base on degree, hour and rain
                            String search = "rain";
                            if (description.toLowerCase().indexOf(search.toLowerCase()) != -1) {
                                rain = true;
                            } else {
                                rain = false;
                            }
                            main.setBackgroundResource(list_background[newran.choose_background(degree_value, newran.hour, rain)]);
                            status.setText(description);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error is", "" + error);
            }
        });
        queue.add(stringRequest);

//        try {
//            Intent intent = getIntent();
//            String[] values = intent.getStringArrayExtra("value");
//            assert values != null;
//            degree_unit = values[0];
//            pressure_unit = values[1];
//            wind_unit = values[2];
//        } catch (Exception e) {
//            System.out.print("No getIntent() in second activity");
//        }

        //Event listening on click setting
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Setting.class);
                startActivityForResult(intent1, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == Setting.RESULT_OK){
                assert data != null;
                String[] result = data.getStringArrayExtra("value");
                assert result != null;
                String t_unit = result[0];
                String p_unit = result[1];
                String w_unit = result[2];
                if (!t_unit.equals(degree_unit)) {
                    degree_unit = t_unit;
                    if (t_unit.equals("\u2103")){
                        degree_value = degree_value*9/5 + 32;
                    }else{
                        degree_value = (degree_value - 32)*5/9;
                    }
                    degree.setText(degree_value + degree_unit);
                }
                if (!w_unit.equals(wind_unit)) {
                    wind_unit = w_unit;
                    if (w_unit.equals("m/s")){
                        wind_value = (int) (wind_value/2.237);
                    }else{
                        wind_value = (int) (wind_value*2.237);
                    }
                    wind.setText(wind_value + wind_unit);
                }
                if (!pressure_unit.equals(p_unit)) {
                    pressure_unit = p_unit;
                    if (p_unit.equals("hPa")){
                        pressure_value = pressure_value*101325;
                    }else{
                        pressure_value = pressure_value/101325;
                    }
                    pressure.setText(pressure_value + pressure_unit);
                }
            }
        }
    }
}

