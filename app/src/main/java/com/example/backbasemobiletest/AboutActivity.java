package com.example.backbasemobiletest;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import android.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class AboutActivity extends AppCompatActivity implements About.View, CustomAdapterListener, OnMapReadyCallback {

    private ProgressBar progressBar;
    private android.view.View errorView;
    // private android.view.View infoContainer;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    private SearchView searchView;
    private static final String TAG = "AboutActivity";
    private GoogleMap mMap;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.map_landscape) != null){
            mTwoPane = true;
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_landscape);
            mapFragment.getMapAsync(this);
        }

        AboutPresenterImpl aboutPresenter = new AboutPresenterImpl(this, this);
        initUI();
        aboutPresenter.getAboutInfo();
    }

    @Override
    public void initUI() {
        progressBar = findViewById(R.id.progressBar);
        errorView = findViewById(R.id.errorView);
        //infoContainer = findViewById(R.id.infoContainer);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter();
        recyclerView.setAdapter(customAdapter);
        recyclerView.setHasFixedSize(true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.sv_search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                customAdapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public void getCities(List<AboutInfo> aboutInfos) {
        customAdapter.setDataSet(aboutInfos, this);
    }

    @Override
    public void showError() {
        errorView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(android.view.View.GONE);
    }

    @Override
    public void onCitySelected(AboutInfo aboutInfo) {
        if (mTwoPane && mMap != null) {
            createCityMarker(aboutInfo.getCoord().getLat(),aboutInfo.getCoord().getLon());

        } else {
            Intent intent = new Intent(this, CityActivity.class);
            intent.putExtra(CityActivity.KEY_LAT, aboutInfo.getCoord().getLat());
            intent.putExtra(CityActivity.KEY_LON, aboutInfo.getCoord().getLon());
            startActivity(intent);

        }
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
    }
}
