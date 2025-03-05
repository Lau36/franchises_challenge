package com.example.franquicias.franquicias_prueba.infrastructure.out.repository;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface IProductRespository extends R2dbcRepository<ProductEntity, Long> {
    Mono<Boolean> existsProductByName(String productName);
}
