package com.example.practica.mapbox;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;

public class ParkingPoisViewHolder extends RecyclerView.ViewHolder {
    private TextView nombreTextView;
    private TextView latitudTextView;
    private TextView longitudTextView;

    public ParkingPoisViewHolder(@NonNull View itemView) {
        super(itemView);
        nombreTextView = itemView.findViewById(R.id.tvParking);
        latitudTextView = itemView.findViewById(R.id.tvParkingLong);
        longitudTextView = itemView.findViewById(R.id.tvParkingLat);
    }

    public void bindData(ParkingPOIS parkingPOI) {
        nombreTextView.setText(parkingPOI.getNombre());
        latitudTextView.setText(String.valueOf(parkingPOI.getLatitud()));
        longitudTextView.setText(String.valueOf(parkingPOI.getLongitud()));
    }
}
