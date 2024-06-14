package com.example.listadecomprassqlite.Reposotory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.listadecomprassqlite.Models.ListaModel;
import com.example.listadecomprassqlite.Models.ProdutoModel;
import com.example.listadecomprassqlite.Uteis.DataBaseUtil;

import java.util.ArrayList;
import java.util.List;

public class ListaRepository {
    DataBaseUtil dataBaseUtil;
    private static final String TABLE_NAME = "Lista";

    public ListaRepository(Context context){
        dataBaseUtil =  new DataBaseUtil(context);
    }

    public void Salvar(ListaModel listaModel){
        ContentValues contentValues =  new ContentValues();

        contentValues.put("descricao",  listaModel.getDescricao());
        contentValues.put("dataCriacao", listaModel.getDataCriacao());

        dataBaseUtil.GetConexaoDataBase().insert(TABLE_NAME,null,contentValues);
    }

    public void Atualizar(ListaModel listaModel){
        ContentValues contentValues =  new ContentValues();

        contentValues.put("descricao",  listaModel.getDescricao());
        contentValues.put("dataCriacao", listaModel.getDataCriacao());

        dataBaseUtil.GetConexaoDataBase().update(
                TABLE_NAME,
                contentValues,
                "id = ?",
                new String[]{Integer.toString(listaModel.getId())}
        );
    }

    public Integer Excluir(int id){
        return dataBaseUtil.GetConexaoDataBase().delete(
                TABLE_NAME,
                "id = ?",
                new String[]{Integer.toString(id)}
        );
    }

    public ListaModel GetLista(int id) {
        Cursor cursor = dataBaseUtil.GetConexaoDataBase().rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE id= " + id,
                null
        );

        ListaModel listaModel = null;

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int descricaoIndex = cursor.getColumnIndex("descricao");
            int dataCriacaoIndex = cursor.getColumnIndex("dataCriacao");

            if (idIndex != -1 && descricaoIndex != -1 && dataCriacaoIndex != -1) {
                listaModel = new ListaModel();

                listaModel.setId(cursor.getInt(idIndex));
                listaModel.setDescricao(cursor.getString(descricaoIndex));
                listaModel.setDataCriacao(cursor.getString(dataCriacaoIndex));
            }
        }

        cursor.close();
        return listaModel;
    }

    public List<ListaModel> SelecionarTodos(){
        List<ListaModel> listas = new ArrayList<ListaModel>();

        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id,         ");
        stringBuilderQuery.append("        descricao,  ");
        stringBuilderQuery.append("        dataCriacao ");
        stringBuilderQuery.append("  FROM " + TABLE_NAME);
        stringBuilderQuery.append(" ORDER BY descricao ");

        Cursor cursor = dataBaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        ListaModel listaModel;

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int idIndex = cursor.getColumnIndex("id");
                int descricaoIndex = cursor.getColumnIndex("descricao");
                int dataCriacaoIndex = cursor.getColumnIndex("dataCriacao");

                if (idIndex != -1 && descricaoIndex != -1 && dataCriacaoIndex != -1) {
                    listaModel = new ListaModel();

                    listaModel.setId(cursor.getInt(idIndex));
                    listaModel.setDescricao(cursor.getString(descricaoIndex));
                    listaModel.setDataCriacao(cursor.getString(dataCriacaoIndex));

                    listas.add(listaModel);
                }

                cursor.moveToNext();
            }
        }

        return listas;
    }

}
