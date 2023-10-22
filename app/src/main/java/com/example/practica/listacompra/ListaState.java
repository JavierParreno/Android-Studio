package com.example.practica.listacompra;

import java.util.List;

public class ListaState {

    private List<String> items;

    public ListaState(List<String> items) {
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }
}
