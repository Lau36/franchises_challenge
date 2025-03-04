package com.example.franquicias.franquicias_prueba.domain.models;

public class Franchise {

    private Long id;
    private String name;

    public Franchise(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


}
