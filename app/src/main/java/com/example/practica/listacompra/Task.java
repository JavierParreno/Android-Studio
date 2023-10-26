package com.example.practica.listacompra;
import android.net.Uri;

public class Task {

    private boolean taskCompleted;
    private String text;
    private Uri imageUri;

    public Task(String text, boolean taskCompleted, Uri imageUri) {
        this.text = text;
        this.taskCompleted = taskCompleted;
        this.imageUri = imageUri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return taskCompleted;
    }

    public void setCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
