package com.example.practica;

public class Model {
    private  String imageUrl;

    public Model() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Model(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
