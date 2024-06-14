package com.example.listadecomprassqlite.Reposotory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listadecomprassqlite.Models.ProdutoModel;
import com.example.listadecomprassqlite.Uteis.DataBaseUtil;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    DataBaseUtil dataBaseUtil;
    private static final String TABLE_NAME = "Produto";

    public ProdutoRepository(Context context){
        dataBaseUtil = new DataBaseUtil(context);
    }

    public void Salvar(ProdutoModel produtoModel){
        ContentValues contentValues =  new ContentValues();

        contentValues.put("nome",  produtoModel.getNome());
        contentValues.put("valor", produtoModel.getValor());

        dataBaseUtil.GetConexaoDataBase().insert(TABLE_NAME,null,contentValues);
    }

    public void Atualizar(ProdutoModel produtoModel){
        ContentValues contentValues =  new ContentValues();

        contentValues.put("nome",  produtoModel.getNome());
        contentValues.put("valor", produtoModel.getValor());

        dataBaseUtil.GetConexaoDataBase().update(
                TABLE_NAME,
                contentValues,
                "id = ?",
                new String[]{Integer.toString(produtoModel.getId())}
        );
    }

    public Integer Excluir(int id){
        return dataBaseUtil.GetConexaoDataBase().delete(
                TABLE_NAME,
                "id = ?",
                new String[]{Integer.toString(id)}
        );
    }

    public ProdutoModel GetProduto(int id) {
        Cursor cursor = dataBaseUtil.GetConexaoDataBase().rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE id= " + id,
                null
        );

        ProdutoModel produtoModel = null;

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nomeIndex = cursor.getColumnIndex("nome");
            int valorIndex = cursor.getColumnIndex("valor");

            if (idIndex != -1 && nomeIndex != -1 && valorIndex != -1) {
                produtoModel = new ProdutoModel();

                produtoModel.setId(cursor.getInt(idIndex));
                produtoModel.setNome(cursor.getString(nomeIndex));
                produtoModel.setValor(cursor.getDouble(valorIndex));
            }
        }

        cursor.close();
        return produtoModel;
    }

    public List<ProdutoModel> SelecionarTodos(){
        List<ProdutoModel> produtos = new ArrayList<ProdutoModel>();

        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id,         ");
        stringBuilderQuery.append("        nome,       ");
        stringBuilderQuery.append("        valor       ");
        stringBuilderQuery.append("  FROM " + TABLE_NAME);
        stringBuilderQuery.append(" ORDER BY nome      ");

        Cursor cursor = dataBaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        ProdutoModel produtoModel;

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int idIndex = cursor.getColumnIndex("id");
                int nomeIndex = cursor.getColumnIndex("nome");
                int valorIndex = cursor.getColumnIndex("valor");

                if (idIndex != -1 && nomeIndex != -1 && valorIndex != -1) {
                    produtoModel = new ProdutoModel();

                    produtoModel.setId(cursor.getInt(idIndex));
                    produtoModel.setNome(cursor.getString(nomeIndex));
                    produtoModel.setValor(cursor.getDouble(valorIndex));

                    produtos.add(produtoModel);
                }

                cursor.moveToNext();
            }
        }

        return produtos;
    }
}