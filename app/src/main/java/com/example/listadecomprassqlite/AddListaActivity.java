package com.example.listadecomprassqlite;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadecomprassqlite.Models.ListaModel;
import com.example.listadecomprassqlite.Models.ProdutoModel;
import com.example.listadecomprassqlite.Repository.ListaRepository;
import com.example.listadecomprassqlite.Repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;

public class AddListaActivity extends AppCompatActivity {
    private ListaRepository listaRepository;
    private ProdutoRepository produtoRepository;
    private EditText etListName;
    private RecyclerView recyclerViewProducts;
    private ProdutoAdapter produtoAdapter;
    private Button btnSave;
    private List<ProdutoModel> produtos;
    private List<ProdutoModel> selectedProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        listaRepository = new ListaRepository(this);
        produtoRepository = new ProdutoRepository(this);

        etListName = findViewById(R.id.etListName);
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveList();
            }
        });

        loadProdutos();
    }

    private void loadProdutos() {
        produtos = produtoRepository.getAll();
        selectedProdutos = new ArrayList<>();
        if (produtos != null) {
            for (ProdutoModel produto : produtos) {
                Log.d("AddListaActivity", "Produto carregado: " + produto.getNome());
            }
        }
        produtoAdapter = new ProdutoAdapter(produtos);
        recyclerViewProducts.setAdapter(produtoAdapter);
    }

    private void saveList() {
        String nome = etListName.getText().toString();
        if (nome.isEmpty()) {
            Toast.makeText(this, "O nome da lista não pode estar vazio", Toast.LENGTH_SHORT).show();
            return;
        }

        String dataAtual = listaRepository.getCurrentDate();
        double total = calculateTotal();
        ListaModel novaLista = new ListaModel(0, nome, dataAtual, total);
        long listaId = listaRepository.insert(novaLista);

        for (ProdutoModel produto : produtos) {
            if (produto.isSelecionado()) {
                produto.setListaId((int) listaId);
                produtoRepository.insert(produto);
                selectedProdutos.add(produto);
            }
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
}