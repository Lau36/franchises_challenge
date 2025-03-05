package com.example.franquicias.franquicias_prueba.aplication.impl;

import com.example.franquicias.franquicias_prueba.aplication.IProductRest;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IProductServicePort;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ProductRest implements IProductRest {

    private final IProductServicePort productServicePort;

    @Override
    public Mono<Void> addNewProduct(Product product) {
        return productServicePort.addNewProduct(product);
    }
}
