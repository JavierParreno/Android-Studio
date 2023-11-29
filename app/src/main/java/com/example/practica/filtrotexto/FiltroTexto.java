package com.example.practica.filtrotexto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.practica.R;

import java.util.ArrayList;
import java.util.List;

public class FiltroTexto extends AppCompatActivity {

    EditText etBuscador;
    RecyclerView rvLista;
    AdaptadorUsuarios adaptador;
    List<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_texto);
   //     getSupportActionBar().hide();

        etBuscador = findViewById(R.id.etBuscador);
        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrar(editable.toString());

            }
        });

        rvLista = findViewById(R.id.rvLista);
        rvLista.setLayoutManager(new GridLayoutManager(this, 1));

        listaUsuarios = new ArrayList<>();
        listaUsuarios.add (new Usuario("ana", "123456", "aa@aa.com"));
        listaUsuarios.add (new Usuario("anaba", "54564", "aaa@aaa.com"));
        listaUsuarios.add (new Usuario("bb", "4896", "bb@bb.com"));
        listaUsuarios.add (new Usuario("javi", "12788883456", "jj@jj.com"));
        listaUsuarios.add (new Usuario("juna", "1234534546", "hh@ggg.com"));
        listaUsuarios.add (new Usuario("jaaa", "123443456", "gggg@gggg.com"));

        adaptador = new AdaptadorUsuarios(FiltroTexto.this, listaUsuarios);
        rvLista.setAdapter(adaptador);
    }

    public void filtrar(String text){
        ArrayList<com.example.practica.filtrotexto.Usuario> filtrarLista = new ArrayList<>();
        for(Usuario usuario : listaUsuarios){
            if(usuario.getNombre().toLowerCase().contains(text.toLowerCase())){
                filtrarLista.add(usuario);
            }
        }
        adaptador.filtrar(filtrarLista);
    }
}