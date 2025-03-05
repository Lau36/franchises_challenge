package com.example.franquicias.franquicias_prueba.domain.ports.out;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {
    Mono<Product> addProduct(Product product);
    Mono<Void> saveProductBranch(Long productId, Product product);
    Mono<Boolean> existsProductByName(String productName);
    Mono<Void> deleteProductById(Long productId);
}
