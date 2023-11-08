package com.example.practica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

public class MapaGoogle extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_google);

        // Obtener una referencia al fragmento de mapa y registrar un callback para obtener el objeto GoogleMap
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Aquí es donde puedes acceder a la lista de ubicaciones (POIs) pasada desde la actividad Mapa
        ArrayList<Pois> poisList = (ArrayList<Pois>) getIntent().getSerializableExtra("ubicaciones");

        // Ahora puedes trabajar con la lista de POIs en MapaGoogle
        if (poisList != null) {
            // Realiza las operaciones necesarias con la lista de POIs
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // El mapa está listo, puedes utilizar googleMap para realizar operaciones con el mapa.
        mMap = googleMap;
        // Configura el mapa según tus necesidades aquí.
    }
}