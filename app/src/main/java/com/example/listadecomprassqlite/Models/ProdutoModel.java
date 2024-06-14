package com.example.listadecomprassqlite.Models;

import java.util.List;

public class ProdutoModel {
    public ProdutoModel(){

    }
    public ProdutoModel(Integer id, String nome, double valor){
        setId(id);
        setNome(nome);
        setValor(valor);
    }

    private Integer id;
    private String nome;
    private double valor;
    private List<ListaModel> listas;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public double getValor(){
        return valor;
    }

    public void setValor(double valor){
        this.valor = valor;
    }
}
