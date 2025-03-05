package com.example.franquicias.franquicias_prueba.domain.models;

public class ProductBranch {
    private Long id;
    private Long productId;
    private Long branchId;
    private Integer stock;

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
