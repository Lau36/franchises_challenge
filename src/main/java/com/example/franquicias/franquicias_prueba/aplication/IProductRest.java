package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import reactor.core.publisher.Mono;

public interface IProductRest {
    Mono<Void> addNewProduct(Product product);
}
