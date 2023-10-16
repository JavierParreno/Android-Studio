package com.example.practica.listacompra;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.practica.R; // Asegúrate de que la importación de R sea correcta

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
        taskList.add(new Task("Llamar al médico", false));
        taskList.add(new Task("Hacer ejercicio", false));
        taskList.add(new Task("Comprar frutas", false));
        taskList.add(new Task("Estudiar programación", false));

        taskAdapter = new TaskAdapter(taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Configurar la funcionalidad para eliminar tareas al deslizar
        TaskItemTouchHelper itemTouchHelper = new TaskItemTouchHelper(taskAdapter);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(int position) {
        // Mostrar un AlertDialog para editar la tarea
        Task task = taskList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Tarea");

        // Aquí puedes agregar un EditText u otros elementos para editar la tarea
        // Por ejemplo:
        /*
        final EditText editText = new EditText(this);
        editText.setText(task.getText());
        builder.setView(editText);
        */

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            // Obtener el nuevo texto de editText (si se usa)
            // String nuevoTexto = editText.getText().toString();

            // Actualiza la tarea con los nuevos datos
            // task.setText(nuevoTexto);
            taskAdapter.notifyItemChanged(position);
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    @Override
    public void onCheckBoxClick(int position) {
        // Marcar o desmarcar la tarea como completada
        Task task = taskList.get(position);
        task.setCompleted(!task.isCompleted());
        taskAdapter.notifyItemChanged(position);
    }
}
