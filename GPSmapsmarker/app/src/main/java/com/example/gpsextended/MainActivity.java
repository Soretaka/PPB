package com.example.gpsextended;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private final static int ZOOM_GOOGLE_MAP = 15;
    private final static int UPDATE_INTERVAL_IN_MILISECONDS = 10000;
    private final static int UPDATE_DISTANCE_IN_METERS = 1;
    private final static String LATITUDE_LABEL = "lat: ";
    private final static String LONGITUDE_LABEL = "lng: ";
    private final static String TIME_UPDATE_LABEL = "update: ";
    private GoogleMap gMap;
    private LocationListener myLocationListener;
    private LocationManager myLocationManager;
    private Location myCurrentLocation;
    private Boolean mRequestingLocationUpdates;
    private TextView txtlat, txtlng, txttime;
    private Button btnStart, btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a handle to the fragment and register the callback.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        // TextView
        txtlat = findViewById(R.id.latitude);
        txtlng = findViewById(R.id.longitude);
        txttime = findViewById(R.id.time);

        // Buttons
        btnStart = findViewById(R.id.start);
        btnStart.setOnClickListener(op);
        btnStop = findViewById(R.id.stop);
        btnStop.setOnClickListener(op);

        // Initial Value
        mRequestingLocationUpdates = false;
        updateButtons();
    }

    View.OnClickListener op = view -> {
        if (view.getId() == R.id.start) {
            startUpdates();
        } else if (view.getId() == R.id.stop) {
            stopUpdates();
        }
    };

    private void startUpdates() {
        if (!mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private void stopUpdates() {
        if (mRequestingLocationUpdates) {
            stopLocationUpdates();
        }
    }

    protected void startLocationUpdates() {
        gMap.clear();
        myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocationListener = new locationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
        else {
            myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_INTERVAL_IN_MILISECONDS, UPDATE_DISTANCE_IN_METERS, myLocationListener);
            mRequestingLocationUpdates = true;
            updateButtons();
            Toast.makeText(this, "Update Location Started", Toast.LENGTH_SHORT).show();
        }
    }

    protected void stopLocationUpdates(){
        myLocationManager.removeUpdates(myLocationListener);
        myLocationManager = null;
        mRequestingLocationUpdates = false;
        updateButtons();
        Toast.makeText(this, "Update Location Stopped", Toast.LENGTH_SHORT).show();
    }

    protected void requestPermission(){
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this, permission, 1);
    }

    private void updateUI(){
        txtlat.setText(String.format("%s%s", LATITUDE_LABEL, myCurrentLocation.getLatitude()));
        txtlng.setText(String.format("%s%s", LONGITUDE_LABEL, myCurrentLocation.getLongitude()));
        txttime.setText(String.format("%s%s", TIME_UPDATE_LABEL, DateFormat.getTimeInstance().format(new Date())));

        LatLng myLocation = new LatLng(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude());
        gMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, ZOOM_GOOGLE_MAP));
    }

    private void updateButtons(){
        if(mRequestingLocationUpdates){
            btnStart.setEnabled(false);
            btnStop.setEnabled(true);
        }
        else{
            btnStart.setEnabled(true);
            btnStop.setEnabled(false);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }

    private class locationListener implements LocationListener{
        @Override
        public void onLocationChanged(@NonNull Location location) {
            myCurrentLocation = location;
            updateUI();
            Toast.makeText(getBaseContext(), "Location Updated", Toast.LENGTH_LONG).show();
        }
    }

}