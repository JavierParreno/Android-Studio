package com.example.practica.listacompra;

import android.net.Uri;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Task {
    private boolean taskCompleted;
    private String text;
    private String imageUriString; // Cambiar el tipo a String

    public Task() {
    }

    public Task(String text, boolean taskCompleted, Uri imageUri) {
        this.text = text;
        this.taskCompleted = taskCompleted;
        if (imageUri != null) {
            this.imageUriString = imageUri.toString(); // Convertir Uri a String
        } else {
            this.imageUriString = null;
        }
    }

    public boolean isTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Uri getImageUri() {
        if (imageUriString != null) {
            return Uri.parse(imageUriString); // Convertir String a Uri
        } else {
            return null;
        }
    }

    public void setImageUri(Uri imageUri) {
        if (imageUri != null) {
            this.imageUriString = imageUri.toString();
        } else {
            this.imageUriString = null;
        }
    }

    public static List<Task> deserialize(String serializedList) {
        // Deserializa la cadena JSON en una lista de tareas
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Task>>() {}.getType();
        List<Task> taskList = gson.fromJson(serializedList, listType);

        // Corregir la conversión de String a Uri después de la deserialización
        for (Task task : taskList) {
            Uri imageUri = task.getImageUri();
            task.setImageUri(imageUri);
        }

        return taskList;
    }

    public static String serialize(List<Task> taskList) {
        // Serializa la lista de tareas a JSON
        Gson gson = new Gson();
        return gson.toJson(taskList);
    }
}
