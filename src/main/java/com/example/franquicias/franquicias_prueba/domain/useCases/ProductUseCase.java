package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IProductServicePort;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IProductPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.BranchValidations;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.ProductValidations;
import reactor.core.publisher.Mono;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;
    private final ProductValidations productValidations;
    private final BranchValidations branchValidations;
    private final FranchiseValidations franchiseValidations;

    public ProductUseCase(IProductPersistencePort productPersistencePort,
                          ProductValidations productValidations,
                          BranchValidations branchValidations,
                          FranchiseValidations franchiseValidations) {
        this.productPersistencePort = productPersistencePort;
        this.productValidations = productValidations;
        this.branchValidations = branchValidations;
        this.franchiseValidations = franchiseValidations;
    }

    @Override
    public Mono<Void> addNewProduct(Product product) {

        return Mono.when(productValidations.validateProductName(product.getName()),
                        branchValidations.validateExistsBranchById(product.getBranchId()))
                .then(Mono.defer(() -> productPersistencePort.addProduct(product)))
                .flatMap( productSaved -> productPersistencePort.saveProductBranch(productSaved.getId(), product)
                                .onErrorResume(
                                        error -> productPersistencePort.deleteProductById(productSaved.getId())
                                        .then(Mono.error(error))
                                )

                        )
                .then();
    }

    @Override
    public Mono<Void> deleteProduct(Long productId, Long branchId) {
        return productPersistencePort.deleteProductById(productId)
                .then(productPersistencePort.deleteProductBranchByIds(productId, branchId));
    }

    @Override
    public Mono<ProductStockByFranchise> getProducst(Long franchiseId) {
        return franchiseValidations.validateFranchiseExistsById(franchiseId)
                .then(productPersistencePort.getProductStockByFranchiseId(franchiseId));
    }


}
