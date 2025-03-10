package com.example.franquicias.franquicias_prueba.infrastructure.out.adapter;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IProductPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStock;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.ProductBranchEntity;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.ProductEntity;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IProductBranchRepository;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IProductRespository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@AllArgsConstructor
public class ProductAdapter implements IProductPersistencePort {
    private final IProductBranchRepository productBranchRepository;
    private final IProductRespository productRespository;

    @Override
    public Mono<Product> addProduct(Product product) {
        Product newProduct = new Product();
        return productRespository.save(ProductEntity.builder()
                        .name(product.getName())
                        .id(product.getId())
                .build()).map(productSaved -> {
                newProduct.setName(productSaved.getName());
                newProduct.setId(productSaved.getId());
            return newProduct;
        } );
    }

    @Override
    public Mono<Void> saveProductBranch(Long productId, Product product) {
        return productBranchRepository.save(ProductBranchEntity.builder()
                .productId(productId)
                .branchId(product.getBranchId())
                .stock(product.getStock())
                .build()).then();
    }


    @Override
    public Mono<Boolean> existsProductByName(String productName) {
        return productRespository.existsProductByName(productName);
    }

    @Override
    public Mono<Void> deleteProductById(Long productId) {
        return productRespository.deleteById(productId);
    }

    @Override
    public Mono<Void> deleteProductBranchByIds(Long productId, Long branchId) {
        return productBranchRepository.deleteByProductIdAndBranchId(productId, branchId);
    }

    @Override
    public Mono<ProductStockByFranchise> getProductStockByFranchiseId(Long franchiseId) {
        ProductStockByFranchise productsWithFranchiseId = new ProductStockByFranchise();

        return productRespository.findTopStockedProductsByFranchiseId(franchiseId)
                .collectList()
                .flatMap(
                productList -> {
                    List<ProductStock> productStockList = productList.stream().map(
                            dto -> new ProductStock(dto.getName(), dto.getBranchName(), dto.getStock())
                    ).toList();
                    productsWithFranchiseId.setFranchiseId(franchiseId);
                    productsWithFranchiseId.setProducts(productStockList);

                    return Mono.just(productsWithFranchiseId);
                });
    }

    @Override
    public Mono<ProductBranch> findProductBranchByIds(Long productId, Long branchId) {
        ProductBranch newProduct = new ProductBranch();
        return productBranchRepository.findByProductIdAndBranchId(productId, branchId).flatMap(
                product -> {
                    newProduct.setId(product.getId());
                    newProduct.setBranchId(product.getBranchId());
                    newProduct.setProductId(product.getProductId());
                    newProduct.setStock(product.getStock());

                    return Mono.just(newProduct);
                }
        );
    }

    @Override
    public Mono<ProductBranch> updateProduct(ProductBranch productBranch) {
        ProductBranchEntity entity = ProductBranchEntity.builder()
                .id(productBranch.getId())
                .stock(productBranch.getStock())
                .productId(productBranch.getProductId())
                .branchId(productBranch.getBranchId())
                .build();
        ProductBranch productBranchUpdated = new ProductBranch();
        return productBranchRepository.save(entity).map(
                productSaved -> {
                    productBranchUpdated.setBranchId(productSaved.getBranchId());
                    productBranchUpdated.setProductId(productSaved.getProductId());
                    productBranchUpdated.setStock(productSaved.getStock());
                    productBranchUpdated.setId(productSaved.getId());
                    return productBranchUpdated;
                }
        );
    }

    @Override
    public Mono<Product> findProductById(Long productId) {
        Product product = new Product();
        return productRespository.findById(productId).map(
                productEntity -> {
                    product.setId(productEntity.getId());
                    product.setName(productEntity.getName());
                    return product;
                }
        );
    }


}
