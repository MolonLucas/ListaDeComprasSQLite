package com.example.listadecomprassqlite.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listadecomprassqlite.Models.ProdutoModel;
import com.example.listadecomprassqlite.Uteis.DataBaseUtil;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    private SQLiteDatabase db;

    public ProdutoRepository(Context context) {
        DataBaseUtil dbHelper = new DataBaseUtil(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ProdutoModel produto) {
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("preco", produto.getPreco());
        values.put("listaId", produto.getListaId());
        return db.insert("produto", null, values);
    }

    public List<ProdutoModel> getAll() {
        List<ProdutoModel> produtos = new ArrayList<>();
        String[] columns = {"id", "nome", "preco", "listaId"};

        Cursor cursor = db.query("produto", columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ProdutoModel produto = new ProdutoModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("preco")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("listaId"))
                );
                produtos.add(produto);
                Log.d("ProdutoRepository", "Produto carregado: " + produto.getNome());
            } while (cursor.moveToNext());
        }
        cursor.close();
        return produtos;
    }

    public List<ProdutoModel> getByListaId(int listaId) {
        List<ProdutoModel> produtos = new ArrayList<>();
        String[] columns = {"id", "nome", "preco", "listaId"};
        String selection = "listaId = ?";
        String[] selectionArgs = {String.valueOf(listaId)};

        Cursor cursor = db.query("produto", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ProdutoModel produto = new ProdutoModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("preco")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("listaId"))
                );
                produtos.add(produto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return produtos;
    }

    public int update(ProdutoModel produto) {
        ContentValues values = new ContentValues();
        values.put("nome", produto.getNome());
        values.put("preco", produto.getPreco());
        values.put("listaId", produto.getListaId());
        String where = "id = ?";
        String[] whereArgs = {String.valueOf(produto.getId())};
        return db.update("produto", values, where, whereArgs);
    }

    public int delete(int id) {
        String where = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        return db.delete("produto", where, whereArgs);
    }
}