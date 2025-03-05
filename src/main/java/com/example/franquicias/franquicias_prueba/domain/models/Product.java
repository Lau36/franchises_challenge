package com.example.franquicias.franquicias_prueba.domain.models;

public class Product {

    private Long id;
    private String name;
    private Integer cuantityInStock;
    private Long branchId;

    public Product(Long id, String name, Integer cuantityInStock, Long branchId) {
        this.id = id;
        this.name = name;
        this.cuantityInStock = cuantityInStock;
        this.branchId = branchId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCuantityInStock() {
        return cuantityInStock;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCuantityInStock(Integer cuantityInStock) {
        this.cuantityInStock = cuantityInStock;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
