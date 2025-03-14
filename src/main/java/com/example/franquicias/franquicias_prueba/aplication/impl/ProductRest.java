package com.example.franquicias.franquicias_prueba.aplication.impl;

import com.example.franquicias.franquicias_prueba.aplication.IProductRest;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IProductServicePort;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductBranchRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.response.ProductStockResponse;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.response.ProductsTopStockResponse;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ProductRest implements IProductRest {

    private final IProductServicePort productServicePort;

    @Override
    public Mono<Void> addNewProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setName(productRequest.getName());
        product.setBranchId((long) productRequest.getBranchId());
        product.setStock(productRequest.getStock());

        return productServicePort.addNewProduct(product);
    }

    @Override
    public Mono<Void> deleteProduct(Long productId, Long branchId) {
        return productServicePort.deleteProduct(productId, branchId);
    }

    @Override
    public Mono<ProductsTopStockResponse> getAllProductStockByFranchise(Long franchiseId) {
        return productServicePort.getProducst(franchiseId).map(products ->
                ProductsTopStockResponse.builder()
                        .franchiseId(franchiseId)
                        .products(products.getProducts().stream().map(
                                productStockResponse ->
                                        ProductStockResponse.builder()
                                                .branchName(productStockResponse.getBranchName())
                                                .name(productStockResponse.getName())
                                                .stock(productStockResponse.getStock())
                                                .build()).toList()
                        )
                        .build());
    }

    @Override
    public Mono<Void> updateStock(ProductBranchRequest productBranchRequest) {
        ProductBranch productBranch = new ProductBranch();
        productBranch.setBranchId(productBranchRequest.getBranchId());
        productBranch.setProductId(productBranchRequest.getProductId());
        productBranch.setStock(productBranchRequest.getStock());
        return productServicePort.updateStock(productBranch).then();
    }

    @Override
    public Mono<Void> updateProductName(Long id, String name) {
        return productServicePort.updateProductName(id, name);
    }
}
