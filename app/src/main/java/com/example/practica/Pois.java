package com.example.practica;

import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;


public class Pois extends MarkerOptions implements Serializable {
    private String titulo;
    private double latitud;
    private double longitud;

    public Pois(String titulo, double latitud, double longitud) {
        this.titulo = titulo;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}