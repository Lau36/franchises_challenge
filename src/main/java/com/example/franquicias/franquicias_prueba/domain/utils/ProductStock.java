package com.example.franquicias.franquicias_prueba.domain.utils;

public class ProductStock {
    private String name;
    private String branchName;
    private Integer stock;

    public ProductStock(String name, String branchName, Integer stock) {
        this.name = name;
        this.branchName = branchName;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public String getBranchName() {
        return branchName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
