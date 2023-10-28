package com.example.practica;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.practica.listacompra.ListaCompra;
import com.google.android.material.navigation.NavigationView;


public class Foto extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Button btnCamara;
    private ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        Toolbar toolbar = findViewById(R.id.toolbar); // Asegúrate de que tengas un elemento Toolbar en tu layout

        // Configurar el botón de hamburguesa (ícono de menú)
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout); // Asegúrate de que tengas un elemento DrawerLayout en tu layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        btnCamara = findViewById(R.id.btnCamera);
        ivFoto = findViewById(R.id.ivFoto);

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

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camerai = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camerai, 123);

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Se busca que el codigo conincida
        if (requestCode == 123) {
            // Se guarda la foto en una variable tipo bitmap
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // Se muestra la imagen en el imageview
            ivFoto.setImageBitmap(photo);
        }
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

}
