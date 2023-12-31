package com.example.practica.mapagoogle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practica.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Mapa extends AppCompatActivity {
    private EditText etTitulo, etLongitud, etLatitud;
    private Button btnAgregar, btnGuardar, btnMapa;
    private int nextId = 1;
    private ArrayList<Pois> poisList = new ArrayList<>();

    @Override
    protected void onPause() {
        super.onPause();

        // Guarda la lista de POIs en SharedPreferences
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(poisList);
        editor.putString("poisList", json);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Recupera la lista de POIs desde SharedPreferences
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("poisList", null);
        Type type = new TypeToken<ArrayList<Pois>>() {}.getType();
        poisList = gson.fromJson(json, type);

        if (poisList == null) {
            poisList = new ArrayList<>();
        }

        etTitulo = findViewById(R.id.etTitulo);
        etLongitud = findViewById(R.id.etLongitud);
        etLatitud = findViewById(R.id.etLatitud);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnMapa = findViewById(R.id.btnMapa);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener latitud y longitud desde los campos de entrada
                double latitude = Double.parseDouble(etLatitud.getText().toString());
                double longitude = Double.parseDouble(etLongitud.getText().toString());

                // Obtener un nuevo id (asegúrate de tener una lógica para generar un id único)

                // Agregar un POI a la lista
                poisList.add(new Pois(nextId, etTitulo.getText().toString(), latitude, longitude));
                nextId++;

                // Limpiar los campos después de agregar un POI
                etTitulo.setText("");
                etLatitud.setText("");
                etLongitud.setText("");
            }
        });

        // Configurar el botón "Guardar" (puedes agregar lógica de guardado si es necesario)
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Agrega el POI actual a la lista.
                poisList.add(new Pois(getNextId(),etTitulo.getText().toString(), Double.parseDouble(etLatitud.getText().toString()), Double.parseDouble(etLongitud.getText().toString())));
            }
        });

        // Configurar el botón "Ir al Mapa"
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Mapa.this, MapaGoogle.class);

                // Agrega los datos de ubicación necesarios como extras en el Intent
                // Puedes usar putExtra para pasar datos, por ejemplo, un ArrayList de ubicaciones
                intent.putParcelableArrayListExtra("ubicaciones", poisList);

                // Inicia la actividad MapaGoogle
                startActivity(intent);
            }
        });
    }


    private long getNextId() {
        long maxId = 0;
        for (Pois poi : poisList) {
            if (poi.getId() > maxId) {
                maxId = poi.getId();
            }
        }
        return maxId + 1;
    }
}
