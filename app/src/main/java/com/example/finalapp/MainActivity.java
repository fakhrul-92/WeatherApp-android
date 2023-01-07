package com.example.finalapp;


import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private ChargerStatusReceiverInnerExample chargerStatusReceiver;
    public String cityName = "";
    String cond = "";
    double windspeed;
    int temperature;
    String cityname = "";
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter chargerActions = new IntentFilter();

        chargerActions.addAction(Intent.ACTION_POWER_CONNECTED);
        chargerActions.addAction(Intent.ACTION_POWER_DISCONNECTED);

        chargerStatusReceiver = new ChargerStatusReceiverInnerExample();
        registerReceiver(chargerStatusReceiver, chargerActions);


    }


    private class ChargerStatusReceiverInnerExample extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
                Toast.makeText(context, "Power connected", Toast.LENGTH_LONG).show();

            }
            else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
                Toast.makeText(context, "Power disconnected", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("temp", temperature);
        savedInstanceState.putDouble("speed", windspeed);
        savedInstanceState.putString("city", cityname);
        savedInstanceState.putString("condition", cond);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        temperature = savedInstanceState.getInt("temp");
        cityname = savedInstanceState.getString("city");
        windspeed = savedInstanceState.getDouble("speed");
        cond = savedInstanceState.getString("condition");
        TextView temperatureView = findViewById(R.id.temperature);
        temperatureView.setText("" + temperature + "°C");
        TextView city = findViewById(R.id.city);
        city.setText(cityname);
        TextView weatherdescriptionView = findViewById(R.id.sunny);
        weatherdescriptionView.setText(cond);
        TextView windspeedView = findViewById(R.id.speed);
        windspeedView.setText("" + windspeed + " m/s");
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void search(View view) {
        EditText searchbar = findViewById(R.id.search);
        cityName = searchbar.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&units=metric&appid=372f58cdd4578e431749980646541770";
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                    parseJsonAndUpdateUI(response);
                }, error -> {
            Toast.makeText(this, "Some error", Toast.LENGTH_LONG).show();

        });
        queue.add(stringRequest);
    }

    private void parseJsonAndUpdateUI(String response) {
        cond = "";
        windspeed = 0;
        temperature = 0;
        cityname = "";

        try {
            JSONObject weather = new JSONObject(response);
            temperature = weather.getJSONObject("main").getInt("temp");
            cond = weather.getJSONArray("weather").getJSONObject(0).getString("main");
            windspeed = weather.getJSONObject("wind").getDouble("speed");
            cityname = weather.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView city = findViewById(R.id.city);
        city.setText(cityname);
        TextView weatherdescriptionView = findViewById(R.id.sunny);
        weatherdescriptionView.setText(cond);
        TextView windspeedView = findViewById(R.id.speed);
        windspeedView.setText("" + windspeed + " m/s");
        TextView temperatureView = findViewById(R.id.temperature);
        temperatureView.setText("" + temperature + "°C");
    }


    public void locate(View view) {
        Intent intent = new Intent (this, location.class);
        startActivity(intent);
    }

    public void startlotto(View view) {
        Intent intent = new Intent (this, LotteryMachine.class);
        startActivity(intent);
    }
}
