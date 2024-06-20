package com.example.listadecomprassqlite.Uteis;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseUtil extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shopping_list.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DataBaseUtil", "onCreate: Creating database");

        String createTableListas = "CREATE TABLE lista (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "data TEXT," +
                "total REAL)";
        db.execSQL(createTableListas);

        String createTableProdutos = "CREATE TABLE produto (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "preco REAL," +
                "listaId INTEGER," +
                "FOREIGN KEY(listaId) REFERENCES lista(id))";
        db.execSQL(createTableProdutos);

        // Inserir produtos padrão
        insertDefaultProducts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS produto");
        db.execSQL("DROP TABLE IF EXISTS lista");
        onCreate(db);
    }

    private void insertDefaultProducts(SQLiteDatabase db) {
        insertProduct(db, "Arroz 1 Kg", 2.69);
        insertProduct(db, "Leite longa vida", 2.70);
        insertProduct(db, "Carne Friboi", 16.70);
        insertProduct(db, "Feijão carioquinha 1 Kg", 3.38);
        insertProduct(db, "Refrigerante coca-cola 2 litros", 3.00);
        insertProduct(db, "Macarrão Espaguete 500g", 4.20);
        insertProduct(db, "Óleo de Soja 900ml", 7.50);
        insertProduct(db, "Açúcar Cristal 1 Kg", 2.80);
        insertProduct(db, "Sal Refinado 1 Kg", 1.50);
        insertProduct(db, "Café Torrado e Moído 500g", 8.90);

        Log.d("DataBaseUtil", "Produtos padrão inseridos");
    }
    private void insertProduct(SQLiteDatabase db, String nome, double preco) {
        Log.d("DataBaseUtil", "insertProduct: Inserting product - " + nome + " (R$ " + preco + ")");
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("preco", preco);
        values.put("listaId", -1); // Produtos padrão não pertencem a nenhuma lista específica
        db.insert("produto", null, values);
    }
}