package com.example.practica;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapaGoogle extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_google);

        // Obtén la lista de POIs desde el Intent
        ArrayList<Pois> poisList = (ArrayList<Pois>) getIntent().getSerializableExtra("ubicaciones");

        // Obtener una referencia al fragmento de mapa y registrar un callback para obtener el objeto GoogleMap
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Configura el mapa según tus necesidades aquí.
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // Puedes ajustar el tipo de mapa según tus preferencias
        mMap.getUiSettings().setZoomControlsEnabled(true); // Habilita controles de zoom

        // Obtén la lista de POIs desde el Intent
        ArrayList<Pois> poisList = (ArrayList<Pois>) getIntent().getSerializableExtra("ubicaciones");

        // Verifica si la lista de POIs no es nula y no está vacía
        if (poisList != null && !poisList.isEmpty()) {
            // Mueve la cámara al primer POI en la lista (puedes ajustar según tus necesidades)
            LatLng firstPoiLatLng = new LatLng(poisList.get(0).getLatitud(), poisList.get(0).getLongitud());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPoiLatLng, 12)); // 12 es un nivel de zoom arbitrario, ajusta según sea necesario

            // Agrega marcadores para cada POI en la lista
            for (Pois poi : poisList) {
                LatLng poiLatLng = new LatLng(poi.getLatitud(), poi.getLongitud());
                mMap.addMarker(new MarkerOptions().position(poiLatLng).title(poi.getTitulo()));
            }
        } else {
            // Maneja el caso en el que la lista de POIs sea nula o esté vacía
            Log.e("MapaGoogle", "La lista de POIs es nula o está vacía");
        }
    }


}
