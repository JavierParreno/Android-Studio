    package com.example.practica;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Toast;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;

    public class UsuarioReg extends AppCompatActivity {
        private static final int PICK_IMAGE_REQUEST = 1;
        private ImageView ivPerfil;
        private Button btnSeleccionarFoto, btnModificar, btnFirebase;
        private EditText etNombre, etEmail;
        private Uri selectedImageUri;
        private FirebaseAuth firebaseAuth;
        private FirebaseDatabase firebaseDatabase;
        private FirebaseStorage firebaseStorage;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_usuario_reg);

            ivPerfil = findViewById(R.id.ivPerfil);
            btnSeleccionarFoto = findViewById(R.id.btnSeleccionarFoto);
            btnModificar = findViewById(R.id.btnModificar);
            etNombre = findViewById(R.id.etNombre);
            etEmail = findViewById(R.id.etEmail);
            btnFirebase = findViewById(R.id.btnFirebase);

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseStorage = FirebaseStorage.getInstance();

            btnSeleccionarFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Abre la galería para seleccionar una foto
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
            });

            btnFirebase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Abre la galería para seleccionar una foto
                    Intent i = new Intent(UsuarioReg.this, SubirImagenFirebase.class);
                    startActivity(i);
                }
            });

            btnModificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String nombre = etNombre.getText().toString().trim();
                    final String email = etEmail.getText().toString().trim();

                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    if (currentUser != null) {
                        String uid = currentUser.getUid();

                        if (selectedImageUri != null) {
                            // Subir la foto a Firebase Storage
                            StorageReference storageReference = firebaseStorage.getReference().child("fotos_perfil/" + selectedImageUri.getLastPathSegment());
                            storageReference.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // URL de la foto en Firebase Storage
                                            String fotoUrl = uri.toString();

                                            // Actualiza los datos del usuario en Firebase Realtime Database
                                            DatabaseReference usuarioRef = firebaseDatabase.getReference("usuarios").child(uid);
                                            Usuario usuario = new Usuario(nombre, email, fotoUrl);
                                            usuarioRef.setValue(usuario);

                                            Toast.makeText(UsuarioReg.this, "Datos actualizados con éxito", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        } else {
                            // Si no se selecciona una nueva foto, solo actualiza los datos en Firebase Realtime Database
                            DatabaseReference usuarioRef = firebaseDatabase.getReference("usuarios").child(uid);
                            Usuario usuario = new Usuario(nombre, email, currentUser.getPhotoUrl().toString()); // Usa la URL de la foto actual
                            usuarioRef.setValue(usuario);

                            Toast.makeText(UsuarioReg.this, "Datos actualizados con éxito", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
                selectedImageUri = data.getData();
                ivPerfil.setImageURI(selectedImageUri);
            }
        }
    }
