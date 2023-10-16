package com.example.practica.listacompra;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnItemClickListener mListener;

    public TaskAdapter(List<Task> taskList, OnItemClickListener listener) {
        this.taskList = taskList;
        this.mListener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.taskText.setText(task.getText());
        holder.taskCheckbox.setChecked(task.isCompleted());

        holder.taskCheckbox.setOnClickListener(view -> mListener.onCheckBoxClick(position));
        holder.itemView.setOnClickListener(view -> mListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void removeTask(int position) {
    }

    public interface OnItemClickListener {
        void onCheckBoxClick(int position);
        void onItemClick(int position);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView taskText;
        public CheckBox taskCheckbox;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskTextView);
            taskCheckbox = itemView.findViewById(R.id.taskCheckBox);
        }
    }
}
