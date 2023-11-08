package com.example.practica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Mapa extends AppCompatActivity  {
    private GoogleMap mMap;
    private EditText etTitulo;
    private EditText etLongitud;
    private EditText etLatitud;
    private Button btnAgregar;
    private Button btnGuardar;
    private Button btnMapa;
    private ArrayList<Pois> poisList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obtener referencias a las vistas en tu diseño XML
        etTitulo = findViewById(R.id.etTitulo);
        etLongitud = findViewById(R.id.etLongitud);
        etLatitud = findViewById(R.id.etLatitud);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnMapa = findViewById(R.id.btnMapa);

        // Configurar el botón "Agregar"
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener latitud y longitud desde los campos de entrada
                double latitude = Double.parseDouble(etLatitud.getText().toString());
                double longitude = Double.parseDouble(etLongitud.getText().toString());

                // Agregar un marcador al mapa con el título y las coordenadas proporcionadas
                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(etTitulo.getText().toString()));

                // Agregar un POI a la lista
                poisList.add(new Pois(etTitulo.getText().toString(), latitude, longitude));



            }
        });

        // Configurar el botón "Guardar" (puedes agregar lógica de guardado si es necesario)
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes implementar la lógica para guardar los POIs en la lista.
                // Agrega el POI actual a la lista.
                poisList.add(new Pois(etTitulo.getText().toString(), Double.parseDouble(etLatitud.getText().toString()), Double.parseDouble(etLongitud.getText().toString())));
            }
        });

        // Configurar el botón "Ir al Mapa"
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mapa.this, MapaGoogle.class);

                // Agrega los datos de ubicación necesarios como extras en el Intent
                // Puedes usar putExtra para pasar datos, por ejemplo, un ArrayList de ubicaciones
                intent.putExtra("ubicaciones", poisList);

                // Inicia la actividad MapaGoogle
                startActivity(intent);
            }
        });
    }
}
