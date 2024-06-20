package com.example.listadecomprassqlite.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.text.SimpleDateFormat;

import com.example.listadecomprassqlite.Models.ListaModel;
import com.example.listadecomprassqlite.Uteis.DataBaseUtil;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaRepository {
    private SQLiteDatabase db;

    public ListaRepository(Context context) {
        DataBaseUtil dbHelper = new DataBaseUtil(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ListaModel lista) {
        ContentValues values = new ContentValues();
        values.put("nome", lista.getNome());
        values.put("data", lista.getData());
        values.put("total", lista.getTotal());
        return db.insert("lista", null, values);
    }

    public List<ListaModel> getAll() {
        List<ListaModel> listas = new ArrayList<>();
        String[] columns = {"id", "nome", "data", "total"};
        Cursor cursor = db.query("lista", columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ListaModel lista = new ListaModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                        cursor.getString(cursor.getColumnIndexOrThrow("data")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("total"))
                );
                listas.add(lista);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listas;
    }

    public ListaModel getById(int id) {
        ListaModel lista = null;
        String[] columns = {"id", "nome", "data", "total"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query("lista", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            lista = new ListaModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nome")),
                    cursor.getString(cursor.getColumnIndexOrThrow("data")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("total"))
            );
        }
        cursor.close();
        return lista;
    }

    public int update(ListaModel lista) {
        ContentValues values = new ContentValues();
        values.put("nome", lista.getNome());
        values.put("data", lista.getData());
        values.put("total", lista.getTotal());
        String where = "id = ?";
        String[] whereArgs = {String.valueOf(lista.getId())};
        return db.update("lista", values, where, whereArgs);
    }

    public int delete(int id) {
        String where = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        return db.delete("lista", where, whereArgs);
    }

    public String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }
}