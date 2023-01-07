package com.example.finalapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class location extends AppCompatActivity implements LocationListener{
    double latitude = 00.0;
    double longitude = 00.0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putFloat("lat", (float) latitude);
        savedInstanceState.putFloat("long", (float) longitude);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        latitude = savedInstanceState.getFloat("lat");
        longitude = savedInstanceState.getFloat("long");
        TextView gpsTextView = findViewById(R.id.result);
        gpsTextView.setText("Latitude: " + (Math.round(latitude * 100.00) / 100.00) + "\n" + "Longitude: " + (Math.round(longitude * 100.0) / 100.0));
    }


    public void show(View view) {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    0
            );
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        TextView gpsTextView = findViewById(R.id.result);
        gpsTextView.setText("Latitude: " + (Math.round(latitude * 100.00) / 100.00) + "\n" + "Longitude: " + (Math.round(longitude * 100.0) / 100.0));
    }

}