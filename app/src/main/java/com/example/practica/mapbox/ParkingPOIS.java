package com.example.practica.mapbox;

public class ParkingPOIS {
    private String nombre;
    private double latitud;
    private double longitud;

    private String id;

    public ParkingPOIS() {
        // Constructor vacío requerido por Firebase
    }

    public ParkingPOIS(String nombre, double latitud, double longitud, String id) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
