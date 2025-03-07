package com.example.franquicias.franquicias_prueba.domain.models;

public class Branch {
    private Long id;
    private String name;
    private Long franchiseId;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getFranchiseId() {
        return franchiseId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFranchiseId(Long franchiseId) {
        this.franchiseId = franchiseId;
    }
}
