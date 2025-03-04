package com.example.franquicias.franquicias_prueba.infrastructure.out.mapper;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.ProductEntity;

public interface IProductMapper {
    ProductEntity toProductEntity(Product product);
    Product toProduct(ProductEntity productEntity);
}
