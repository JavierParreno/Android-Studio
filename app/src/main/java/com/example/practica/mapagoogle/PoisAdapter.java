package com.example.practica.mapagoogle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;

import java.util.ArrayList;
public class PoisAdapter extends RecyclerView.Adapter<PoisAdapter.PoisViewHolder> {
    public ArrayList<Pois> poisList;
    private OnItemClickListener onItemClickListener;

    public PoisAdapter(ArrayList<Pois> poisList, OnItemClickListener onItemClickListener) {
        this.poisList = poisList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PoisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pois, parent, false);
        return new PoisViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PoisViewHolder holder, int position) {
        Pois poi = poisList.get(position);
        holder.bind(poi);
    }

    @Override
    public int getItemCount() {
        return poisList.size();
    }

    // Resto del código de PoisAdapter

    public static class PoisViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView coordinatesTextView;

        public PoisViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            coordinatesTextView = itemView.findViewById(R.id.coordinatesTextView);

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
        }

        public void bind(Pois poi) {
            titleTextView.setText(poi.getTitulo());
            coordinatesTextView.setText(String.format("(%f, %f)", poi.getLatitud(), poi.getLongitud()));
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public void updateData(ArrayList<Pois> newPoisList) {
        this.poisList = newPoisList;
        notifyDataSetChanged();
    }

    public Pois getItem(int position) {
        return poisList.get(position);
    }
    public void removeItem(int position) {
        poisList.remove(position);
        notifyItemRemoved(position);
    }


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onModifyClick(int position);

        void onDeleteClick(int position);
    }
}
