package com.example.listadecomprassqlite.Uteis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseUtil extends SQLiteOpenHelper{
    private static final String NOME_BASE_DE_DADOS   = "LISTADECOMPRAS.db";

    private static final int VERSAO_BASE_DE_DADOS = 1;

    public DataBaseUtil(Context context){
        super(context, NOME_BASE_DE_DADOS, null, VERSAO_BASE_DE_DADOS);
    }

    /*NA INICIALIZAÇÃO DA CLASSE VAMOS CRIAR A TABELA QUE VAMOS USAR*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder stringBuilderCreateTable = new StringBuilder();

        stringBuilderCreateTable.append(" CREATE TABLE Produto (");
        stringBuilderCreateTable.append("        id      INTEGER PRIMARY KEY AUTOINCREMENT,       ");
        stringBuilderCreateTable.append("        nome    TEXT    NOT NULL,                        ");
        stringBuilderCreateTable.append("        valor   REAL    NOT NULL )                       ");

        db.execSQL(stringBuilderCreateTable.toString());

        stringBuilderCreateTable.append(" CREATE TABLE Lista (");
        stringBuilderCreateTable.append("        id            INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilderCreateTable.append("        descricao     TEXT    NOT NULL,                  ");
        stringBuilderCreateTable.append("        dataCriacao   TEXT    NOT NULL )                 ");

        db.execSQL(stringBuilderCreateTable.toString());

        stringBuilderCreateTable.append(" CREATE TABLE ProdutoLista (");
        stringBuilderCreateTable.append("        id          INTEGER PRIMARY KEY AUTOINCREMENT,   ");
        stringBuilderCreateTable.append("        idProduto   INTEGER    NOT NULL,                 ");
        stringBuilderCreateTable.append("        idLista     INTEGER    NOT NULL,                 ");
        stringBuilderCreateTable.append("        FOREIGN KEY(idProduto) REFERENCES Produto(id),   ");
        stringBuilderCreateTable.append("        FOREIGN KEY(idLista)   REFERENCES Lista(id) )      ");

        db.execSQL(stringBuilderCreateTable.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ProdutoLista");
        db.execSQL("DROP TABLE IF EXISTS Produto");
        db.execSQL("DROP TABLE IF EXISTS Lista");
        onCreate(db);
    }

    public SQLiteDatabase GetConexaoDataBase(){

        return this.getWritableDatabase();
    }
}
