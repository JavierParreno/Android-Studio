package com.example.practica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Mapa extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        SupportMapFragment mapFragment = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng madrid = new LatLng(40.4165, -3.70256);
        LatLng punto1 = new LatLng(41.4165, -4.70256);
        LatLng punto2 = new LatLng(42.4165, -5.70256);

        mMap.addMarker(new MarkerOptions()
                .position(madrid)
                .title("Mi posicion"));

        mMap.addMarker(new MarkerOptions()
                .position(punto1)
                .title("Posicion1"));

        mMap.addMarker(new MarkerOptions()
                .position(punto2)
                .title("Posicion 2"));

    }
}