package com.example.practica.listacompra;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;
    private OnItemClickListener mListener;

    public TaskAdapter(Context context, List<Task> taskList, OnItemClickListener listener) {
        this.context = context;
        this.taskList = taskList;
        this.mListener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task currentTask = taskList.get(position);

        // Asocia los elementos de la tarea con las vistas en task_item.xml
        holder.taskText.setText(currentTask.getText());
        holder.taskCheckbox.setChecked(currentTask.isCompleted());

        holder.btnSeleccionarImagen.setOnClickListener(view -> mListener.onSelectImageClick(position)); // Usa el método onSelectImageClick
        holder.taskCheckbox.setOnClickListener(view -> mListener.onCheckBoxClick(position));
        holder.itemView.setOnClickListener(view -> mListener.onItemClick(position));
    }

    public void selectImage(int position, Uri selectedImageUri) {
        // Asocia la URI de la imagen a la tarea actual
        Task task = taskList.get(position);
        task.setImageUri(selectedImageUri);
        notifyItemChanged(position); // Cambia taskAdapter.notifyItemChanged(position) a notifyItemChanged(position)
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void removeTask(int position) {
        // Implementa la lógica para eliminar una tarea si es necesario
    }

    public interface OnItemClickListener {
        void onCheckBoxClick(int position);
        void onItemClick(int position);
        void onSelectImageClick(int position);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskText;
        public CheckBox taskCheckbox;
        public Button btnSeleccionarImagen;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskTextView);
            taskCheckbox = itemView.findViewById(R.id.taskCheckBox);
            btnSeleccionarImagen = itemView.findViewById(R.id.btnSeleccionarImagen);
        }
    }
}
