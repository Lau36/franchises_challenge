package com.example.franquicias.franquicias_prueba.domain.ports.in;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import reactor.core.publisher.Mono;

public interface IProductServicePort {
    Mono<Void> addNewProduct(Product product);
    Mono<Void> deleteProduct(Long productId, Long branchId);
    Mono<ProductStockByFranchise> getProducst(Long franchiseId);
    Mono<ProductBranch> updateStock(ProductBranch productBranch);

}
