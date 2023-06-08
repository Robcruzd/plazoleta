package com.pragma.plazoleta.domain.model;

public class StatusOrderModel {

    private int id;
    private String name;

    public StatusOrderModel() {
    }

    public StatusOrderModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
