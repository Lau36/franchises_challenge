package com.example.franquicias.franquicias_prueba.domain.ports.out;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import reactor.core.publisher.Mono;

public interface IProductPersistencePort {
    Mono<Product> addProduct(Product product);
    Mono<Void> saveProductBranch(Long productId, Product product);
    Mono<Boolean> existsProductByName(String productName);
    Mono<Void> deleteProductById(Long productId);
    Mono<Void> deleteProductBranchByIds(Long productId, Long branchId);
    Mono<ProductStockByFranchise> getProductStockByFranchiseId(Long franchiseId);
    Mono<ProductBranch> findProductBranchByIds(Long productId, Long branchId);
    Mono<ProductBranch> updateProduct(ProductBranch productBranch);
}
