package com.example.listadecomprassqlite.Models;

import java.util.List;

public class ListaModel {
    public ListaModel(){

    }
    public ListaModel(Integer id, String descricao, String dataCriacao){
        setId(id);
        setDescricao(descricao);
        setDataCriacao(dataCriacao);
    }

    private Integer id;
    private String descricao;
    private String dataCriacao;
    private List<ProdutoModel> produtos;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getDescricao(){
        return descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getDataCriacao(){
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao){
        this.dataCriacao = dataCriacao;
    }
}
