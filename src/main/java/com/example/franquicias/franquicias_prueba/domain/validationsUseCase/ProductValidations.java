package com.example.franquicias.franquicias_prueba.domain.validationsUseCase;

import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IProductPersistencePort;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.*;

public class ProductValidations {

    private final IProductPersistencePort productPersistencePort;

    public ProductValidations(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    public Mono<Void> validateProductName(String productName) {
        return productPersistencePort.existsProductByName(productName)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new AlreadyExistsException(String.format(ALREADY_EXIST_PRODUCT, productName))))
                .then();
    }

    public Mono<ProductBranch> findProductBrach(ProductBranch productBranch){
        return productPersistencePort.findProductBranchByIds(productBranch.getProductId(), productBranch.getBranchId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        String.format(PRODUCT_BRANCH_NOT_FOUND, productBranch.getProductId(), productBranch.getBranchId())
                )));
    }

    public Mono<Product> findProduct(Long id){
        return productPersistencePort.findProductById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        String.format(PRODUCT_NOT_FOUND, id)
                )));
    }
}
