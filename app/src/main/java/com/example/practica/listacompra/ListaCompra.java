package com.example.practica.listacompra;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.example.practica.R;


import androidx.core.view.GravityCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.provider.MediaStore;
import android.net.Uri;
import android.widget.ImageView;



public class ListaCompra extends AppCompatActivity implements TaskAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);

        Toolbar toolbar = findViewById(R.id.toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("MisSharedPreferences", Context.MODE_PRIVATE);

        String serializedList = sharedPreferences.getString("lista_state", null);
        if (serializedList != null) {
            taskList = deserialize(serializedList);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        recyclerView = findViewById(R.id.recyclerView);

        Button btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);
        ImageView imgTarea = findViewById(R.id.imgTarea);

        btnSeleccionarImagen.setOnClickListener(v -> {
            // Abre la galería de imágenes
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Agregar 5 tareas de ejemplo
        taskList.add(new Task("Tarea 1", false, null));
        taskList.add(new Task("Tarea 2", false, null));
        taskList.add(new Task("Tarea 3", false, null));
        taskList.add(new Task("Tarea 4", false, null));

        taskAdapter = new TaskAdapter(taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Configurar la funcionalidad para eliminar tareas al deslizar
        TaskItemTouchHelper itemTouchHelper = new TaskItemTouchHelper(taskAdapter);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

        // Agregar una tarea al hacer clic en el botón "Agregar Tarea"
        findViewById(R.id.btnAgregarTarea).setOnClickListener(view -> showAddTaskDialog());



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        restoreListFromSharedPreferences();
    }

    private void saveListToSharedPreferences() {
        // Obtén una referencia a SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MisSharedPreferences", Context.MODE_PRIVATE);

        // Serializa la lista de tareas a JSON
        Gson gson = new Gson();
        String serializedList = gson.toJson(taskList);

        // Guarda el estado de la lista en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lista_state", serializedList);
        editor.apply();
    }

    private List<Task> deserialize(String serializedList) {
        // Deserializa la cadena JSON en una lista de tareas
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Task>>() {}.getType();
        return gson.fromJson(serializedList, listType);
    }

    private void restoreListFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MisSharedPreferences", Context.MODE_PRIVATE);
        String serializedList = sharedPreferences.getString("lista_state", null);

        if (serializedList != null) {
            taskList = deserialize(serializedList);
            taskAdapter = new TaskAdapter(taskList, this);
            recyclerView.setAdapter(taskAdapter);
        }
    }



    @Override
    public void onItemClick(int position) {
        // Mostrar un AlertDialog para editar la tarea
        Task selectedTask = taskList.get(position);

        // Declarar la variable dialog
        final AlertDialog[] dialog = new AlertDialog[1];

        // Crear un diseño personalizado para el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Tarea");

        // Inflar el diseño personalizado
        View dialogLayout = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
        builder.setView(dialogLayout);

        // Obtener referencias a elementos de diseño dentro del diseño personalizado
        EditText editText = dialogLayout.findViewById(R.id.editText);
        Button btnSave = dialogLayout.findViewById(R.id.btnSave);
        Button btnDelete = dialogLayout.findViewById(R.id.btnDelete);
        Button btnMarkAsCompleted = dialogLayout.findViewById(R.id.btnMarkAsCompleted);
        Button btnCancel = dialogLayout.findViewById(R.id.btnCancel);

        // Establecer el texto actual de la tarea en el EditText
        editText.setText(selectedTask.getText());

        // Configurar acciones para los botones
        btnSave.setOnClickListener(v -> {
            String nuevoTexto = editText.getText().toString();
            selectedTask.setText(nuevoTexto);
            taskAdapter.notifyItemChanged(position);
            dialog[0].dismiss(); // Cierra el AlertDialog después de guardar
        });

        btnDelete.setOnClickListener(v -> {
            taskList.remove(position);
            taskAdapter.notifyItemRemoved(position);
            dialog[0].dismiss(); // Cierra el AlertDialog después de eliminar
        });

        btnMarkAsCompleted.setOnClickListener(v -> {
            selectedTask.setCompleted(true);
            taskAdapter.notifyItemChanged(position);
            dialog[0].dismiss(); // Cierra el AlertDialog después de marcar como realizada
        });

        btnCancel.setOnClickListener(v -> dialog[0].dismiss()); // Cierra el AlertDialog al cancelar

        // Mostrar el AlertDialog
        dialog[0] = builder.create();
        dialog[0].show();
    }

    @Override
    public void onCheckBoxClick(int position) {
        // Marcar o desmarcar la tarea como completada
        Task selectedTask = taskList.get(position);
        selectedTask.setCompleted(!selectedTask.isCompleted());
        taskAdapter.notifyItemChanged(position);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            // Muestra la imagen seleccionada en el ImageView
            ImageView imgTarea = findViewById(R.id.imgTarea);
            imgTarea.setImageURI(selectedImageUri);
            imgTarea.setVisibility(View.VISIBLE);

            // Asocia la URI de la imagen a la tarea actual
            int taskPosition = getTaskPosition(); // Implementa esta función para obtener la posición de la tarea actual
            if (taskPosition != -1) {
                Task task = taskList.get(taskPosition);
                task.setImageUri(selectedImageUri);

            }
        }
    }
    private int getTaskPosition() {
        // Aquí debes implementar la lógica para determinar la posición de la tarea actual
        // Puedes usar algún identificador o índice para relacionar la tarea con la imagen seleccionada.
        // Retorna -1 si no puedes determinar la posición.
        // Ejemplo: Recorrer la lista y comparar con algún identificador único.
        return -1;
    }
    private void showAddTaskDialog() {
        // Crear un cuadro de diálogo para agregar una nueva tarea
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflar el diseño personalizado para el cuadro de diálogo
        View dialogLayout = getLayoutInflater().inflate(R.layout.agregar_tarea, null);
        builder.setView(dialogLayout);

        // Obtener una referencia al EditText en el diseño personalizado
        EditText editText = dialogLayout.findViewById(R.id.editText);

        // Configurar acción para el botón "Aceptar"
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            String nuevaTarea = editText.getText().toString();
            if (!nuevaTarea.isEmpty()) {
                // Crear una nueva tarea y agregarla a la lista
                Task newTask = new Task(nuevaTarea, false, selectedImageUri);

                taskList.add(newTask);
                taskAdapter.notifyItemInserted(taskList.size() - 1); // Notificar al adaptador que se insertó un nuevo elemento

                selectedImageUri = null;
                ImageView imgTarea = findViewById(R.id.imgTarea);
                imgTarea.setImageURI(null);
                imgTarea.setVisibility(View.GONE);
                // Guardar el estado actual de la lista en SharedPreferences
                saveListToSharedPreferences();
            }
        });

        // Configurar acción para el botón "Cancelar"
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.dismiss(); // Cerrar el cuadro de diálogo al cancelar
        });

        // Eliminar el botón neutro (por defecto, "Cancelar")
        builder.setNeutralButton("", null);

        builder.create().show();
    }

    private String serialize(List<Task> taskList) {
        // Aquí debes convertir tu lista de tareas en un formato que se pueda guardar en SharedPreferences, como JSON o similar.
        // Implementa la lógica para la serialización según tus necesidades.
        // Puedes usar librerías de serialización como Gson para simplificar este proceso.
        // Aquí te muestro un ejemplo sencillo utilizando Gson:
        Gson gson = new Gson();
        return gson.toJson(taskList);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
