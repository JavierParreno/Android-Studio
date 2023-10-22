package com.example.practica.listacompra;
import android.net.Uri;

public class Task {

    private boolean taskCompleted; // Mantén el nombre de la variable como taskCompleted
    private String text;
    private Uri imageUri; // Agrega un campo para la URI de la imagen

    public Task(String text, boolean taskCompleted, Uri imageUri) {
        this.text = text;
        this.taskCompleted = taskCompleted; // Asegúrate de usar el nombre correcto
        this.imageUri = imageUri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return taskCompleted; // Usa el nombre correcto
    }

    public void setCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted; // Usa el nombre correcto
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}

