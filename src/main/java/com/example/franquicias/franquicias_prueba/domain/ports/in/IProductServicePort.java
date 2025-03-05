package com.example.franquicias.franquicias_prueba.domain.ports.in;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import reactor.core.publisher.Mono;

public interface IProductServicePort {
    Mono<Void> addNewProduct(Product product);

}
