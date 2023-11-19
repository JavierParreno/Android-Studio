package com.example.practica.mapbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;

import java.util.List;

public class ParkingPoisAdapter extends RecyclerView.Adapter<ParkingPoisAdapter.ParkingPoisViewHolder> {
    private List<ParkingPOIS> parkingList;

    public ParkingPoisAdapter(List<ParkingPOIS> parkingList) {
        this.parkingList = parkingList;
    }

    @NonNull
    @Override
    public ParkingPoisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_parking, parent, false);
        return new ParkingPoisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingPoisViewHolder holder, int position) {
        ParkingPOIS parkingPOI = parkingList.get(position);
        holder.bindData(parkingPOI);
    }

    @Override
    public int getItemCount() {
        return parkingList.size();
    }

    public static class ParkingPoisViewHolder extends RecyclerView.ViewHolder {
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
}
