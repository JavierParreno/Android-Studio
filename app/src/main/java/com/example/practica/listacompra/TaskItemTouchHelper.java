package com.example.practica.listacompra;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TaskItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private TaskAdapter taskAdapter;

    public TaskItemTouchHelper(TaskAdapter taskAdapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.taskAdapter = taskAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // No necesitas implementar esto si no planeas reordenar elementos en la lista
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // Elimina la tarea al deslizar hacia la izquierda o la derecha
        int position = viewHolder.getAdapterPosition();
        taskAdapter.removeTask(position);
    }
}
