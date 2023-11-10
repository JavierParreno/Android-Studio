package com.example.practica.mapagoogle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;

public class MapaGoogle extends FragmentActivity implements OnMapReadyCallback, PoisAdapter.OnItemClickListener {
    private GoogleMap mMap;
    private RecyclerView recyclerView;
    private PoisAdapter poisAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_google);

        // Obtén la lista de POIs desde el Intent
        ArrayList<Pois> poisList = (ArrayList<Pois>) getIntent().getSerializableExtra("ubicaciones");

        // Obtener una referencia al fragmento de mapa y registrar un callback para obtener el objeto GoogleMap
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Asegúrate de tener un adaptador apropiado
        poisAdapter = new PoisAdapter(poisList);
        recyclerView.setAdapter(poisAdapter);

        // Configura el adaptador con la actividad como el escuchador de clics
        poisAdapter.setOnItemClickListener(this);
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
            poisAdapter.updateData(poisList);
        } else {
            // Maneja el caso en el que la lista de POIs sea nula o esté vacía
            Log.e("MapaGoogle", "La lista de POIs es nula o está vacía");
        }
    }
    @Override
    public void onItemClick(int position) {
        // Lógica para mostrar la ubicación en el mapa
        Pois selectedPoi = poisAdapter.getItem(position);
        LatLng poiLatLng = new LatLng(selectedPoi.getLatitud(), selectedPoi.getLongitud());

        // Mueve la cámara a la ubicación seleccionada
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(poiLatLng, 15));
    }

    @Override
    public void onModifyClick(int position) {
        // Lógica para modificar las coordenadas de la ubicación
        // Puedes abrir una nueva actividad o fragmento para permitir al usuario modificar las coordenadas
        // Utiliza poisAdapter.getItem(position) para obtener la ubicación seleccionada
        Pois selectedPoi = poisAdapter.getItem(position);

        // Aquí puedes abrir una nueva actividad o fragmento para permitir al usuario modificar las coordenadas
        // Puedes pasar la información del POI a la nueva actividad o fragmento a través de un Intent
        // Ejemplo:
        Intent modifyIntent = new Intent(MapaGoogle.this, Activitymodifylocation.class);
        modifyIntent.putExtra("selectedPoi", selectedPoi);
        startActivity(modifyIntent);
    }

    @Override
    public void onDeleteClick(int position) {
        // Lógica para borrar la ubicación
        poisAdapter.removeItem(position);

        // También puedes eliminar el marcador del mapa si lo deseas
        // Ejemplo:
        mMap.clear();
        for (Pois poi : poisAdapter.poisList) {
            LatLng poiLatLng = new LatLng(poi.getLatitud(), poi.getLongitud());
            mMap.addMarker(new MarkerOptions().position(poiLatLng).title(poi.getTitulo()));
        }
    }
}