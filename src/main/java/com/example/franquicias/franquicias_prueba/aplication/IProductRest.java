package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import reactor.core.publisher.Mono;

public interface IProductRest {
    Mono<Void> addNewProduct(Product product);
    Mono<Void> deleteProduct(Long productId, Long branchId);
    Mono<ProductStockByFranchise> getAllProductStockByFranchise(Long franchiseId);
    Mono<ProductBranch> updateStock(ProductBranch productBranch);
}
