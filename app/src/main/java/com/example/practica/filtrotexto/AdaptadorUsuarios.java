package com.example.practica.filtrotexto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorUsuarios  extends RecyclerView.Adapter <AdaptadorUsuarios.UsuariosViewHolder> {

    Context context;
    List<Usuario> listaUsuario;

    public AdaptadorUsuarios(Context context, List<Usuario> listaUsuario) {
        this.context = context;
        this.listaUsuario = listaUsuario;
    }

    @NonNull
    @Override
    public AdaptadorUsuarios.UsuariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false);
        return new UsuariosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorUsuarios.UsuariosViewHolder holder, int position) {
        holder.tvNombre.setText(listaUsuario.get(position).getNombre());
        holder.tvTelefono.setText(listaUsuario.get(position).getTelefono());
        holder.tvEmail.setText(listaUsuario.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    public class UsuariosViewHolder extends  RecyclerView.ViewHolder {

        TextView tvNombre, tvTelefono, tvEmail;
        public UsuariosViewHolder (@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvEmail = itemView.findViewById(R.id.tvEmail);

        }
    }
    public void  filtrar (ArrayList<Usuario> filtroUsuarios) {
        this.listaUsuario = filtroUsuarios;
        notifyDataSetChanged();
    }
}
