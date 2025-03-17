package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductBranchRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.response.ProductsTopStockResponse;
import reactor.core.publisher.Mono;

public interface IProductRest {
    Mono<Void> addNewProduct(ProductRequest productRequest);
    Mono<Void> deleteProduct(Long productId, Long branchId);
    Mono<ProductsTopStockResponse> getAllProductStockByFranchise(Long franchiseId);
    Mono<Void> updateStock(ProductBranchRequest productBranchRequest);
    Mono<Void> updateProductName(Long id, String name);
}
