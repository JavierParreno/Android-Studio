package com.example.practica.mapbox;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.practica.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;


import java.util.ArrayList;
import java.util.List;

public class Parking extends AppCompatActivity {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private Style mapStyle;
    private RecyclerView recyclerView;
    private Button btnGuardarParking;
    private DatabaseReference databaseReference;
    private double currentLongitude;
    private double currentLatitude;
    private List<ParkingPOIS> parkingList;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private LocationListener locationListener;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("parking_locations");

        // Inicializar Mapbox
        Mapbox.getInstance(this, getString(R.string.access_token));
        mapView = findViewById(R.id.mvBoxAparcar);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                Parking.this.mapboxMap = mapboxMap;

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapStyle = style;

                        // Agregar una imagen al estilo del mapa
                        mapStyle.addImage("location-icon-id", getResources().getDrawable(R.drawable.ic_location));

                        // Crear un marcador en la ubicación actual y añadirlo al mapa
                        SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, mapStyle);
                        symbolManager.setIconAllowOverlap(true);
                        symbolManager.setTextAllowOverlap(true);
                        symbolManager.create(new SymbolOptions()
                                .withIconImage("location-icon-id")
                                .withIconSize(1.3f)
                                .withGeometry(Point.fromLngLat(currentLongitude, currentLatitude)) // Reemplaza currentLongitude y currentLatitude con las coordenadas de tu ubicación actual
                                .withTextField("Ubicación Actual")
                                .withTextOffset(new Float[] {0f, 1.5f}));
                    }
                });

            }
        });

        // Enlazar vistas
        recyclerView = findViewById(R.id.rvParking);
        btnGuardarParking = findViewById(R.id.btnGuardaParking);

        // Configurar el RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Crear la lista a nivel de clase, si no está declarada
        if (parkingList == null) {
            parkingList = new ArrayList<>();
        }

        // Crear un adaptador para el RecyclerView (usando los datos de Firebase)
        ParkingPoisAdapter adapter = new ParkingPoisAdapter(this, parkingList);
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
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores al acceder a la base de datos
                if (databaseError != null) {
                    // Imprimir el error en el registro o mostrar un mensaje al usuario
                    Log.e("DatabaseError", "Error al acceder a la base de datos: " + databaseError.getMessage());
                    // Puedes mostrar un mensaje de error al usuario si lo deseas
                    Toast.makeText(getApplicationContext(), "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Ubicación GPS
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Utiliza la ubicación actual aquí (location.getLatitude(), location.getLongitude())
                // Puedes usar la ubicación actual en tu lógica para guardar la posición del estacionamiento, etc.
            }

            // Otros métodos de LocationListener
            //...

        };

        // Verificar y solicitar permisos de ubicación
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_CODE);
            return;
        }

        // Solicitar actualizaciones de ubicación
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    // Método para guardar la ubicación (debes implementar tu lógica para guardar en Firebase)
    private void guardarUbicacion() {
        // Generar un ID único para el elemento
        String id = databaseReference.push().getKey();

        // Crear un objeto ParkingPOIS con los datos que deseas guardar, incluyendo el ID
        ParkingPOIS parkingPOI = new ParkingPOIS("Nombre", 123.456, 789.012, id); // Aquí usa tus propios datos

        // Agregar los datos a Firebase Realtime Database
        databaseReference.child(id).setValue(parkingPOI); // Guarda el objeto en la base de datos bajo el ID generado
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

    // Método para manejar la respuesta de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si se otorgan los permisos, solicitar actualizaciones de ubicación
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }


}
