package com.example.practica;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.practica.MainActivity;
import com.example.practica.listacompra.ListaCompra;
import com.google.android.material.navigation.NavigationView;
import com.example.practica.R;

public class Foto extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imgCaptured;
    private TextView textView;
    private Button btnFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_item_home) {
                openHome();
            } else if (id == R.id.menu_item_lista) {
                openListaCompra();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        btnFoto = findViewById(R.id.btnFoto);
        imgCaptured = findViewById(R.id.imgCaptured);
        textView = findViewById(R.id.textView);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openListaCompra() {
        Intent i = new Intent(Foto.this, ListaCompra.class);
        startActivity(i);
    }

    private void openHome() {
        Intent i = new Intent(Foto.this, MainActivity.class);
        startActivity(i);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                // Obten la imagen capturada desde los extras del intent
                Bundle extras = data.getExtras();
                imgCaptured.setImageBitmap((Bitmap) extras.get("data"));

                // Muestra la imagen y el texto
                imgCaptured.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        }
    }
}
