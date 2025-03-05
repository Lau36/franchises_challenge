package com.example.franquicias.franquicias_prueba.infrastructure.out.adapter;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IProductPersistencePort;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.ProductBranchEntity;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.ProductEntity;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IProductBranchRepository;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IProductRespository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ProductAdapter implements IProductPersistencePort {
    private final IProductBranchRepository productBranchRepository;
    private final IProductRespository productRespository;

    @Override
    public Mono<Product> addProduct(Product product) {
        Product newProduct = new Product();
        return productRespository.save(ProductEntity.builder()
                .name(product.getName())
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

//    public Mono<Void> addAnotherProductToBranch(Long productId, Long branchId, int quantity) {
//        return productBranchRepository.findByProductIdAndBranchId(productId, branchId)
//                .flatMap(existingProductBranch -> {
//                    existingProductBranch.(existingProductBranch.getQuantityInStock() + quantity);
//                    return productBranchRepository.save(existingProductBranch);
//                })
//                .switchIfEmpty(
//                        // Si no existe, crear un nuevo registro
//                        productBranchRepository.save(new ProductBranch(productId, branchId, quantity))
//                );
//    }
}
