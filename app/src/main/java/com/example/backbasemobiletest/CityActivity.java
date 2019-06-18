package com.example.backbasemobiletest;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CityActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String KEY_LAT = CityActivity.class.getSimpleName() + "KEY LAT";
    public static final String KEY_LON = CityActivity.class.getSimpleName() + "KEY LON";
    private GoogleMap mMap;
    double lat;
    double lon;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Intent intent = getIntent();
        if (intent != null) {
            lat = intent.getDoubleExtra(KEY_LAT,0.0);
            lon = intent.getDoubleExtra(KEY_LON,0.0);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        textView = findViewById(R.id.tv_back);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void createCityMarker(double lat, double lon) {
        float zoomLevel = 10.0f;
        LatLng city= new LatLng(lat,lon);
        mMap.addMarker(new MarkerOptions().position(city));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city,zoomLevel));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        createCityMarker(lat, lon);
    }
}
