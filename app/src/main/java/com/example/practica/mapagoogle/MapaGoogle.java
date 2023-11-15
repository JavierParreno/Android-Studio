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
    private static final int TUCODIGODESOLICITUD = 1;

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
        poisAdapter = new PoisAdapter(poisList, this);

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
        Log.d("PoisAdapter", "onModifyClick called at position: " + position);
        Pois selectedPoi = poisAdapter.getItem(position);
        if (selectedPoi != null) {
            Intent modifyIntent = new Intent(MapaGoogle.this, Activitymodifylocation.class);
            modifyIntent.putExtra("selectedPoi", selectedPoi);
            startActivityForResult(modifyIntent, TUCODIGODESOLICITUD);
        } else {
            Log.e("MapaGoogle", "selectedPoi es nulo en onModifyClick");
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TUCODIGODESOLICITUD && resultCode == RESULT_OK) {
            // Manejar los resultados devueltos por Activitymodifylocation
            Pois modifiedPoi = (Pois) data.getSerializableExtra("modifiedPoi");
            int position = findPositionInList(modifiedPoi); // Encuentra la posición del POI modificado en la lista

            if (position != -1) {
                // Actualiza el marcador en el mapa
                updateMapMarker(position, modifiedPoi);

                // Actualiza la ubicación en la lista de POIs y notifica al adaptador
                updatePoiInList(position, modifiedPoi);
            } else {
                Log.e("onActivityResult", "No se pudo encontrar la posición del POI modificado en la lista");
            }
        }
    }
    private int findPositionInList(Pois modifiedPoi) {
        for (int i = 0; i < poisAdapter.poisList.size(); i++) {
            Pois poi = poisAdapter.poisList.get(i);
            if (poi.getId() == modifiedPoi.getId()) {
                return i;
            }
        }
        return -1;
        // Devuelve -1 si no se encuentra el POI en la lista
    }

    // Método para actualizar el marcador en el mapa
    private void updateMapMarker(int position, Pois modifiedPoi) {
        LatLng poiLatLng = new LatLng(modifiedPoi.getLatitud(), modifiedPoi.getLongitud());
        mMap.addMarker(new MarkerOptions().position(poiLatLng).title(modifiedPoi.getTitulo()));
    }

    // Método para actualizar la ubicación en la lista de POIs y notificar al adaptador
    private void updatePoiInList(int position, Pois modifiedPoi) {
        poisAdapter.poisList.set(position, modifiedPoi);
        poisAdapter.notifyItemChanged(position);
    }
}