package com.example.practica;

public class Usuario {
    private String nombre;
    private String correo;
    private String fotoUrl;

    public Usuario() {
        // Constructor vacío requerido por Firebase Realtime Database o Firestore.
    }

    public Usuario(String nombre, String correo, String fotoUrl) {
        this.nombre = nombre;
        this.correo = correo;
        this.fotoUrl = fotoUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
