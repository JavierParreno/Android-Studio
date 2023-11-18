package com.example.practica.mapbox;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Parking extends AppCompatActivity {

    private MapView mapView;
    private RecyclerView recyclerView;
    private Button btnGuardarParking;
    private DatabaseReference databaseReference;

    private List<ParkingPOIS> parkingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("parking_locations");

        // Inicializar Mapbox
        Mapbox.getInstance(this, getString(R.string.access_token));

        // Enlazar vistas
        mapView = findViewById(R.id.mvBoxAparcar);
        recyclerView = findViewById(R.id.rvParking);
        btnGuardarParking = findViewById(R.id.btnGuardaParking);

        // Configurar el MapView (puedes añadir la lógica específica de Mapbox aquí)

        // Configurar el RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Configurar un adaptador para el RecyclerView (usando los datos de Firebase)
        // Por ejemplo, si tienes una lista de ubicaciones de estacionamiento llamada 'parkingList':
        ParkingPoisAdapter adapter = new ParkingPoisAdapter(parkingList);
        recyclerView.setAdapter(adapter);

        // Agregar un listener al botón "Guardar Ubicacion" para guardar la ubicación
        btnGuardarParking.setOnClickListener(v -> guardarUbicacion());

        // Escuchar cambios en la base de datos y actualizar la lista
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parkingList.clear(); // Borrar la lista actual para evitar duplicados al actualizarla
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ParkingPOIS parkingPOI = snapshot.getValue(ParkingPOIS.class);
                    parkingList.add(parkingPOI); // Agregar cada objeto a la lista
                }
                // Notificar al adaptador que los datos han cambiado
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores al acceder a la base de datos
            }
        });
    }

    // Método para guardar la ubicación (debes implementar tu lógica para guardar en Firebase)
    private void guardarUbicacion() {
        // Crear un objeto ParkingPOIS con los datos que deseas guardar
        ParkingPOIS parkingPOI = new ParkingPOIS("Nombre", 123.456, 789.012); // Aquí usa tus propios datos

        // Agregar los datos a Firebase Realtime Database
        databaseReference.push().setValue(parkingPOI); // Guarda el objeto en la base de datos
    }

    // Métodos del ciclo de vida del MapView (necesario para Mapbox)
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
