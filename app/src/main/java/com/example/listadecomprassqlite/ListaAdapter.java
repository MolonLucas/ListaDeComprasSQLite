package com.example.listadecomprassqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadecomprassqlite.Models.ListaModel;

import java.util.List;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {
    private List<ListaModel> listas;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ListaAdapter(List<ListaModel> listas) {
        this.listas = listas;
    }

    public void updateListas(List<ListaModel> novasListas) {
        this.listas = novasListas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
        return new ListaViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        ListaModel lista = listas.get(position);
        holder.nome.setText(lista.getNome());
        holder.data.setText(lista.getData());
        holder.total.setText(String.valueOf(lista.getTotal()));
    }

    @Override
    public int getItemCount() {
        return listas.size();
    }

    static class ListaViewHolder extends RecyclerView.ViewHolder {
        TextView nome, data, total;
        Button btnDelete;

        public ListaViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            nome = itemView.findViewById(R.id.tvListName);
            data = itemView.findViewById(R.id.tvListDate);
            total = itemView.findViewById(R.id.tvTotalPrice);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
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
}