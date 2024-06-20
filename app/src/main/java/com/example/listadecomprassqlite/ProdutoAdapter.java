package com.example.listadecomprassqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.listadecomprassqlite.Models.ProdutoModel;
import com.example.listadecomprassqlite.R;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {
    private List<ProdutoModel> produtos;

    public ProdutoAdapter(List<ProdutoModel> produtos) {
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        ProdutoModel produto = produtos.get(position);
        holder.nome.setText(produto.getNome());
        holder.preco.setText(String.format("R$ %.2f", produto.getPreco()));
        holder.checkbox.setChecked(produto.isSelecionado());
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> produto.setSelecionado(isChecked));
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        TextView nome, preco;
        CheckBox checkbox;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.tvProdutoNome);
            preco = itemView.findViewById(R.id.tvProdutoPreco);
            checkbox = itemView.findViewById(R.id.cbSelecionado);
        }
    }
}