package com.example.practica;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.practica.mapagoogle.Mapa;
import com.example.practica.mapbox.Parking;
import com.example.practica.mapbox.MapBox;
import com.example.practica.notificaciones.Notificaciones;
import com.example.practica.filtrotexto.FiltroTexto;
import com.example.practica.webview.WebView;
import com.google.android.material.navigation.NavigationView;

import com.example.practica.listacompra.ListaCompra;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Button btnaLista, btnaFoto, btnRegistro, btnSubirFotoFire, btnMapa, btnMapBox, btnParkin,btnWebView, btnNotificaciones, btnTextoPredictivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        btnaLista = (Button) findViewById(R.id.btnLista);
        btnaFoto = (Button) findViewById(R.id.btnFoto);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        btnSubirFotoFire = (Button) findViewById(R.id.btnSubirFotoFire);
        btnMapa = (Button) findViewById(R.id.btnMapa);
        btnMapBox = (Button) findViewById(R.id.btnMapBox);
        btnParkin = (Button) findViewById(R.id.btnParking);
        btnWebView = (Button) findViewById(R.id.btnWebView);
        btnNotificaciones =(Button) findViewById(R.id.btnNotificaciones);
        btnTextoPredictivo =(Button) findViewById(R.id.btnPredictivo);

        navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_item_home) {
                // No necesitas hacer nada ya que estás en la actividad principal
            } else if (id == R.id.menu_item_lista) {
                // Abre la actividad ListaCompra
                openListaCompra();
            } else if (id == R.id.menu_item_foto) {
                // Abre la actividad Foto
                openFoto();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        btnaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.practica.MainActivity.this, ListaCompra.class);
                startActivity(i);
            }
        });

        btnaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.practica.MainActivity.this, Foto.class);
                startActivity(i);
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.practica.MainActivity.this, Registro.class);
                startActivity(i);
            }
        });

        btnSubirFotoFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.practica.MainActivity.this, SubirImagenFirebase.class);
                startActivity(i);
            }
        });

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.practica.MainActivity.this, Mapa.class);
                startActivity(i);
            }
        });

        btnMapBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(com.example.practica.MainActivity.this, MapBox.class);
                startActivity(i);
            }
        });

        btnParkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Parking.class);
                startActivity(intent);
            }
        });

        btnWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebView.class);
                startActivity(intent);
            }
        });

        btnNotificaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notificaciones.class);
                startActivity(intent);
            }
        });

        btnTextoPredictivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FiltroTexto.class);
                startActivity(intent);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
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

    private void openListaCompra() {
        Intent i = new Intent(com.example.practica.MainActivity.this, ListaCompra.class);
        startActivity(i);
    }

    private void openFoto() {
        Intent i = new Intent(com.example.practica.MainActivity.this, Foto.class);
        startActivity(i);
    }

}
