package com.example.franquicias.franquicias_prueba.infrastructure.out.repository;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface IProductRepository extends R2dbcRepository<Product, Integer> {
}
