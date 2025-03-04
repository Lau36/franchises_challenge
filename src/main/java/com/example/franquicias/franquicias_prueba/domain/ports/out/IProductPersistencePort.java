package com.example.franquicias.franquicias_prueba.domain.ports.out;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {
    Mono<Void> saveProduct(Product product);
}
