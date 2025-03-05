package com.example.franquicias.franquicias_prueba.domain.utils;

import java.util.List;

public class ProductStockByFranchise {
    private Long franchiseId;
    private List<ProductStock> products;

    public Long getFranchiseId() {
        return franchiseId;
    }

    public List<ProductStock> getProducts() {
        return products;
    }

    public void setFranchiseId(Long franchiseId) {
        this.franchiseId = franchiseId;
    }

    public void setProducts(List<ProductStock> products) {
        this.products = products;
    }
}
