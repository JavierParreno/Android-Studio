package com.example.practica.mapbox;

public class ParkingPOIS {
    private String nombre;
    private double latitud;
    private double longitud;
    // Otros atributos relevantes

    public ParkingPOIS() {
        // Constructor vacío requerido por Firebase
    }

    public ParkingPOIS(String nombre, double latitud, double longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        // Inicializar otros atributos si es necesario
    }

    // Getters y setters para los atributos

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
    // Otros getters y setters si es necesario
}
