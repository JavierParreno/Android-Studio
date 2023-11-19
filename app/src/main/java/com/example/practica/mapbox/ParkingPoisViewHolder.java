package com.example.practica.mapbox;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;

public class ParkingPoisViewHolder extends RecyclerView.ViewHolder {
    private TextView etParking;
    private TextView latitudTextView;
    private TextView longitudTextView;
    private Button eliminarPOI;

    public ParkingPoisViewHolder(@NonNull View itemView) {
        super(itemView);
        etParking = itemView.findViewById(R.id.etParking);
        latitudTextView = itemView.findViewById(R.id.tvParkingLong);
        longitudTextView = itemView.findViewById(R.id.tvParkingLat);
        eliminarPOI = itemView.findViewById(R.id.btneliminarPOI);
    }

    public void bindData(ParkingPOIS parkingPOI) {
        etParking.setText(parkingPOI.getNombre());
        latitudTextView.setText(String.valueOf(parkingPOI.getLatitud()));
        longitudTextView.setText(String.valueOf(parkingPOI.getLongitud()));
    }
}
