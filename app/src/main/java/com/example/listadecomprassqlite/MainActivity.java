package com.example.listadecomprassqlite;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadecomprassqlite.Models.ListaModel;
import com.example.listadecomprassqlite.Repository.ListaRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListaRepository listaRepository;
    private RecyclerView recyclerView;
    private ListaAdapter listaAdapter;
    private List<ListaModel> listas;
    private Button btnCreateNewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaRepository = new ListaRepository(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacingItemDecoration(16));

        btnCreateNewList = findViewById(R.id.btnCreateNewList);
        btnCreateNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddListaActivity.class);
                startActivity(intent);
            }
        });

        loadListas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListas();
    }

    private void loadListas() {
        listas = listaRepository.getAll();
        if (listas != null && !listas.isEmpty()) {
            for (ListaModel lista : listas) {
                Log.d("MainActivity", "Lista carregada: " + lista.getNome());
            }
        } else {
            Log.d("MainActivity", "Nenhuma lista carregada");
        }

        if (listaAdapter == null) {
            listaAdapter = new ListaAdapter(listas);
            listaAdapter.setOnItemClickListener(new ListaAdapter.OnItemClickListener() {
                @Override
                public void onDeleteClick(int position) {
                    deleteList(position);
                }

                @Override
                public void onItemClick(int position) {
                    Log.d("MainActivity", "Item clicado na posição: " + position);
                    Intent intent = new Intent(MainActivity.this, ListaDetailsActivity.class);
                    intent.putExtra("listaId", listas.get(position).getId());
                    Log.d("MainActivity", "Iniciando ListaDetailsActivity com listaId: " + listas.get(position).getId());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(listaAdapter);
        } else {
            listaAdapter.updateListas(listas);
            listaAdapter.notifyDataSetChanged();
        }
    }

    private void deleteList(int position) {
        try {
            ListaModel lista = listas.get(position);
            listaRepository.delete(lista.getId());
            listas.remove(position);
            listaAdapter.notifyItemRemoved(position);
        } catch (Exception e) {
            Log.e("MainActivity", "Erro ao deletar lista", e);
        }
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