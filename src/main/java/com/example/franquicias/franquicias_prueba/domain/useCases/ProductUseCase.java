package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IProductServicePort;
import reactor.core.publisher.Mono;

public class ProductUseCase implements IProductServicePort {

    @Override
    public Mono<Void> saveProduct(Product product) {
        return null;
    }
}
