package com.example.practica.mapagoogle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;

public class PoisViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView coordinatesTextView;
    public Button modifyButton;
    public Button deleteButton;

    private PoisAdapter.OnItemClickListener onItemClickListener;

    public PoisViewHolder(@NonNull View itemView, PoisAdapter.OnItemClickListener listener) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        coordinatesTextView = itemView.findViewById(R.id.coordinatesTextView);
        modifyButton = itemView.findViewById(R.id.modifyButton);
        deleteButton = itemView.findViewById(R.id.deleteButton);
        onItemClickListener = listener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onModifyClick(position);
                    }
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            }
        });
    }
}
