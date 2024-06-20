package com.example.listadecomprassqlite;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadecomprassqlite.Models.ListaModel;
import com.example.listadecomprassqlite.Models.ProdutoModel;
import com.example.listadecomprassqlite.Repository.ListaRepository;
import com.example.listadecomprassqlite.Repository.ProdutoRepository;

import java.util.List;

public class ListaDetailsActivity extends AppCompatActivity {
    private ListaRepository listaRepository;
    private ProdutoRepository produtoRepository;
    private RecyclerView recyclerViewProducts;
    private ProdutoAdapter produtoAdapter;
    private List<ProdutoModel> produtos;
    private EditText etListName;
    private Button btnSave, btnDelete;
    private int listaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_details);

        listaRepository = new ListaRepository(this);
        produtoRepository = new ProdutoRepository(this);

        etListName = findViewById(R.id.etListName);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProducts.addItemDecoration(new SpacingItemDecoration(16));

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveList();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteList();
            }
        });

        listaId = getIntent().getIntExtra("listaId", -1);
        if (listaId != -1) {
            loadListaDetails();
        }
    }

    private void loadListaDetails() {
        ListaModel lista = listaRepository.getById(listaId);
        if (lista != null) {
            etListName.setText(lista.getNome());
            produtos = produtoRepository.getByListaId(listaId);
            produtoAdapter = new ProdutoAdapter(produtos);
            recyclerViewProducts.setAdapter(produtoAdapter);
        } else {
            Log.e("ListaDetailsActivity", "Erro ao carregar detalhes da lista");
        }
    }

    private void saveList() {
        String nome = etListName.getText().toString();
        if (nome.isEmpty()) {
            Toast.makeText(this, "O nome da lista não pode estar vazio", Toast.LENGTH_SHORT).show();
            return;
        }

        ListaModel lista = new ListaModel(listaId, nome, listaRepository.getCurrentDate(), calculateTotal());
        listaRepository.update(lista);
        for (ProdutoModel produto : produtos) {
            produto.setListaId(listaId);
            produtoRepository.update(produto);
        }
        finish();
    }

    private void deleteList() {
        listaRepository.delete(listaId);
        for (ProdutoModel produto : produtos) {
            produtoRepository.delete(produto.getId());
        }
        finish();
    }

    private double calculateTotal() {
        double total = 0.0;
        for (ProdutoModel produto : produtos) {
            if (produto.isSelecionado()) {
                total += produto.getPreco();
            }
        }
        return total;
    }

    private static class SpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spacing;

        public SpacingItemDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = spacing;
        }
    }
}