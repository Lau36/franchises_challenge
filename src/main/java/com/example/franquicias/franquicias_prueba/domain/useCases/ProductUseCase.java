package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IProductServicePort;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IProductPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.BranchValidations;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.ProductValidations;
import reactor.core.publisher.Mono;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final ProductValidations productValidations;
    private final BranchValidations branchValidations;

    public ProductUseCase(IProductPersistencePort productPersistencePort, ProductValidations productValidations, BranchValidations branchValidations) {
        this.productPersistencePort = productPersistencePort;
        this.productValidations = productValidations;
        this.branchValidations = branchValidations;
    }

    @Override
    public Mono<Void> addNewProduct(Product product) {

        return Mono.when(productValidations.validateProductName(product.getName()),
                        branchValidations.validateExistsBranchById(product.getBranchId()))
                .then(Mono.defer(() -> productPersistencePort.addProduct(product)))
                .flatMap( productSaved -> productPersistencePort.saveProductBranch(productSaved.getId(), product)
                                .onErrorResume( error -> productPersistencePort.deleteProductById(productSaved.getId())
                                        .then(Mono.error(error))
                                )

                        )
                .then();
    }

}
