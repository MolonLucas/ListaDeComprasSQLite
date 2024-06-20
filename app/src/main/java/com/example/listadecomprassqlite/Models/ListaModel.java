package com.example.listadecomprassqlite.Models;

import java.util.List;

public class ListaModel {
    private Integer id;
    private String nome;
    private String data;
    private double total;

    public ListaModel(Integer id, String nome, String data, double total) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.total = total;
    }

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}