package com.example.franquicias.franquicias_prueba.aplication.impl;

import com.example.franquicias.franquicias_prueba.aplication.IProductRest;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IProductServicePort;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ProductRest implements IProductRest {

    private final IProductServicePort productServicePort;

    @Override
    public Mono<Void> addNewProduct(Product product) {
        return productServicePort.addNewProduct(product);
    }

    @Override
    public Mono<Void> deleteProduct(Long productId, Long branchId) {
        return productServicePort.deleteProduct(productId, branchId);
    }

    @Override
    public Mono<ProductStockByFranchise> getAllProductStockByFranchise(Long franchiseId) {
        return productServicePort.getProducst(franchiseId);
    }
}
