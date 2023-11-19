package com.example.practica.mapbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.practica.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ParkingPoisAdapter extends RecyclerView.Adapter<ParkingPoisAdapter.ParkingPoisViewHolder> {
    private List<ParkingPOIS> parkingList;
    private Context mContext;

    public ParkingPoisAdapter(Context context, List<ParkingPOIS> parkingList) {
        this.mContext = context;
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

        holder.eliminarPOI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    eliminarPOI(clickedPosition);
                }
            }
        });
    }

    private void eliminarPOI(int position) {
        ParkingPOIS parkingPOI = parkingList.get(position);
        String firebaseKeyToDelete = parkingPOI.getId();

        // Crear un diálogo de confirmación
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("¿Quieres borrar la ubicación?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Eliminar el elemento si se hace clic en Sí
                eliminarUbicacion(position, firebaseKeyToDelete);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada si se hace clic en No
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void eliminarUbicacion(int position, String firebaseKeyToDelete) {
        // Eliminar el elemento de la lista local
        parkingList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, parkingList.size());

        // Eliminar el elemento correspondiente de la base de datos de Firebase
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("parking_locations");
        databaseRef.child(firebaseKeyToDelete).removeValue();
    }


    @Override
    public int getItemCount() {
        return parkingList.size();
    }

    public static class ParkingPoisViewHolder extends RecyclerView.ViewHolder {
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
}
