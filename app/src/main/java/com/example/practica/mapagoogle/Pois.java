package com.example.practica.mapagoogle;

import android.os.Parcel;
import android.os.Parcelable;

public class Pois implements Parcelable {
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

    protected Pois(Parcel in) {
        titulo = in.readString();
        latitud = in.readDouble();
        longitud = in.readDouble();
    }

    public static final Creator<Pois> CREATOR = new Creator<Pois>() {
        @Override
        public Pois createFromParcel(Parcel in) {
            return new Pois(in);
        }

        @Override
        public Pois[] newArray(int size) {
            return new Pois[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
    }
}
