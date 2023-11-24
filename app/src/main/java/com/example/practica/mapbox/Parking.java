package com.example.practica.mapbox;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.maps.Style;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.mapboxsdk.maps.MapView;

import java.util.ArrayList;
import java.util.List;

public class Parking extends AppCompatActivity {
    private MapView mapView;
    private MapboxMap mapboxMap;
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
        Mapbox.getInstance(this, getString(R.string.tokenmapbox));
        setContentView(R.layout.activity_parking);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("parking_locations");

        // Inicializar Mapbox

        mapView = findViewById(R.id.mvBoxAparcar);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(mapboxMap -> {
            if (mapboxMap != null) {
                Parking.this.mapboxMap = mapboxMap;

                // Configurar el estilo del mapa si es necesario
                mapboxMap.setStyle(Style.MAPBOX_STREETS);

                // Agregar un marcador a la ubicación actual con un icono personalizado
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.gps);

                // Listener para cambios de cámara (por ejemplo, desplazamientos)
                mapboxMap.addOnCameraMoveListener(() -> {
                    // Por ejemplo, obtener la nueva posición de la cámara si se desplaza
                    CameraPosition position = mapboxMap.getCameraPosition();
                    LatLng newLatLng = position.target;
                    double newLatitude = newLatLng.getLatitude();
                    double newLongitude = newLatLng.getLongitude();
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
        btnGuardarParking.setOnClickListener(v -> {
            if (currentLatitude != 0 && currentLongitude != 0) {
                mostrarDialogoGuardarUbicacion();
            } else {
                // Avisa al usuario de que la ubicación aún no está disponible
                Toast.makeText(getApplicationContext(), "Ubicación no disponible todavía", Toast.LENGTH_SHORT).show();
            }
        });

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

            private MarkerOptions currentLocationMarker;
            // Dentro del método onLocationChanged del LocationListener
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // Obtener la ubicación actualizada
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();

                // Actualizar la cámara del mapa a la nueva ubicación
                if (mapboxMap != null) {
                    mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(currentLatitude, currentLongitude), 15));

                    // Verificar si ya hay un marcador, si no, crear uno nuevo
                    if (currentLocationMarker == null) {
                        // Agregar un marcador a la ubicación actual con un icono personalizado
                        Drawable drawable = ContextCompat.getDrawable(Parking.this, R.drawable.gps);
                        if (drawable != null) {
                            Bitmap bitmap = BitmapUtils.getBitmapFromDrawable(drawable);
                            if (bitmap != null) {
                                currentLocationMarker = new MarkerOptions()
                                        .position(new LatLng(currentLatitude, currentLongitude))
                                        .icon(IconFactory.getInstance(Parking.this)
                                                .fromBitmap(bitmap));
                                mapboxMap.addMarker(currentLocationMarker);
                            }
                        }
                    } else {
                        // Si ya hay un marcador, actualizar su posición
                        currentLocationMarker.setPosition(new LatLng(currentLatitude, currentLongitude));
                    }
                }
            }


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

    private void guardarUbicacion(double latitude, double longitude, String nombre) {
        // Utilizar el nombre como clave en la base de datos
        databaseReference.child(nombre).setValue(new ParkingPOIS(nombre, latitude, longitude, null));

        // Agregar un marcador con el nombre en el mapa
        if (mapboxMap != null) {
            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .setTitle(nombre)); // Establecer el nombre como título del marcador
        }
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Call super method

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If permissions are granted, request location updates
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    private void mostrarDialogoGuardarUbicacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guardar Ubicación");

        // Crear un EditText para ingresar el nombre de la ubicación
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Botón para guardar la ubicación
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombreUbicacion = input.getText().toString();
                if (!nombreUbicacion.isEmpty()) {
                    guardarUbicacion(currentLatitude, currentLongitude, nombreUbicacion);
                } else {
                    Toast.makeText(getApplicationContext(), "Ingrese un nombre para la ubicación", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón para cancelar
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
}
}