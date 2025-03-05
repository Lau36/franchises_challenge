package com.example.franquicias.franquicias_prueba.domain.validationsUseCase;

import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IProductPersistencePort;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.ALREADY_EXIST_PRODUCT;
import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.PRODUCT_NOT_FOUND;

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

    public Mono<ProductBranch> findProduct(ProductBranch productBranch){
        return productPersistencePort.findProductBranchByIds(productBranch.getProductId(), productBranch.getBranchId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        String.format(PRODUCT_NOT_FOUND, productBranch.getProductId(), productBranch.getBranchId())
                )));
    }
}
