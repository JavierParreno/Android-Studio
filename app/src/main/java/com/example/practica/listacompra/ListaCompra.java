package com.example.practica.listacompra;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.practica.R;

public class ListaCompra extends AppCompatActivity implements TaskAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);

        recyclerView = findViewById(R.id.recyclerView);

        // Agregar 5 tareas de ejemplo
        taskList.add(new Task("Comprar leche", false));
        taskList.add(new Task("Hacer ejercicio", false));
        taskList.add(new Task("Comprar frutas", false));
        taskList.add(new Task("Estudiar programación", false));

        taskAdapter = new TaskAdapter(taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Configurar la funcionalidad para eliminar tareas al deslizar
        TaskItemTouchHelper itemTouchHelper = new TaskItemTouchHelper(taskAdapter);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

        // Agregar una tarea al hacer clic en el botón "Agregar Tarea"
        findViewById(R.id.btnAgregarTarea).setOnClickListener(view -> showAddTaskDialog());
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
        selectedTask.setCompleted(!selectedTask.isCompleted);
        taskAdapter.notifyItemChanged(position);
    }

    private void showAddTaskDialog() {
        // Implementa la lógica para mostrar un cuadro de diálogo para agregar tareas, similar a la opción de edición.
    }
}
