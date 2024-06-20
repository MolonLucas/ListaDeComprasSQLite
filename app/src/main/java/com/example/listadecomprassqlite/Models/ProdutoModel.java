package com.example.listadecomprassqlite.Models;

import java.util.List;

public class ProdutoModel {
    private int id;
    private String nome;
    private double preco;
    private int listaId;
    private boolean selecionado;

    public ProdutoModel(int id, String nome, double preco, int listaId) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.listaId = listaId;
        this.selecionado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getListaId() { // Método para obter o ID da lista
        return listaId;
    }

    public void setListaId(int listaId) { // Método para definir o ID da lista
        this.listaId = listaId;
    }

    public boolean isSelecionado() { // Método para verificar se o produto está selecionado
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) { // Método para definir se o produto está selecionado
        this.selecionado = selecionado;
    }
}