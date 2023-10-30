package com.example.practica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practica.listacompra.ListaCompra;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import android.os.Bundle;

public class Registro extends AppCompatActivity {

    private EditText etNombre, etMail, etPss;
    private Button btnRegistro;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        firebaseAuth = FirebaseAuth.getInstance();

        etNombre = findViewById(R.id.etNombre);
        etMail = findViewById(R.id.etMail);
        etPss = findViewById(R.id.etPss);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String email = etMail.getText().toString();
                String password = etPss.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    // Asignar el nombre al usuario
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nombre)
                                            .build();
                                    user.updateProfile(profileUpdates);

                                    // El usuario se registró exitosamente
                                    Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Hubo un error durante el registro
                                    Toast.makeText(Registro.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    Intent i = new Intent(Registro.this, UsuarioReg.class);
                    startActivity(i);

            }
        });





    }
}