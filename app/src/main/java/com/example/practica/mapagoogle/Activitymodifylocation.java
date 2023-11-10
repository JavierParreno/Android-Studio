package com.example.practica.mapagoogle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practica.R;

public class Activitymodifylocation extends AppCompatActivity {

    private EditText etNewLatitud;
    private EditText etNewLongitud;
    private Button btnSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_location);

        // Obtener referencias a las vistas en tu diseño XML
        etNewLatitud = findViewById(R.id.etNewLatitud);
        etNewLongitud = findViewById(R.id.etNewLongitud);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        // Obtén la ubicación seleccionada desde el Intent
        Pois selectedPoi = (Pois) getIntent().getSerializableExtra("selectedPoi");

        // Puedes usar la ubicación seleccionada para llenar los EditText con las coordenadas actuales
        etNewLatitud.setText(String.valueOf(selectedPoi.getLatitud()));
        etNewLongitud.setText(String.valueOf(selectedPoi.getLongitud()));

        // Configurar el botón "Guardar Cambios"
        btnSaveChanges.setOnClickListener(v -> saveChanges(selectedPoi));
    }

    private void saveChanges(Pois selectedPoi) {
        // Obtener las nuevas coordenadas desde los campos de entrada
        double newLatitud = Double.parseDouble(etNewLatitud.getText().toString());
        double newLongitud = Double.parseDouble(etNewLongitud.getText().toString());

        // Actualizar las coordenadas del POI seleccionado
        selectedPoi.setLatitud(newLatitud);
        selectedPoi.setLongitud(newLongitud);

        // Puedes devolver la ubicación modificada a la actividad anterior (MapaGoogle) usando Intent
        // y finalizar esta actividad si es necesario
        setResult(RESULT_OK, getIntent().putExtra("modifiedPoi", selectedPoi));
        finish();

        // Muestra un mensaje Toast o realiza otras acciones según sea necesario
        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
    }
}
