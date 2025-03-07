package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IProductPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStock;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.BranchValidations;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.ProductValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductUseCaseTest {


    @Mock
    private IProductPersistencePort productPersistencePort;

    @Mock
    private ProductValidations productValidations;

    @Mock
    private BranchValidations branchValidations;

    @Mock
    private FranchiseValidations franchiseValidations;

    @InjectMocks
    private ProductUseCase productUseCase;

    private Product product;
    private ProductBranch productBranch;
    private ProductStockByFranchise productStockByFranchise;
    private ProductStock productsStock;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setBranchId(2L);

        productBranch = new ProductBranch();
        productBranch.setProductId(1L);
        productBranch.setBranchId(2L);
        productBranch.setStock(10);

        productsStock = new ProductStock("product 1", "branch name", 12);

        productStockByFranchise = new ProductStockByFranchise();
        productStockByFranchise.setFranchiseId(1L);
        productStockByFranchise.setProducts(List.of(productsStock));
    }

    @Test
    void addNewProduct_shouldSaveProductTest() {
        when(productValidations.validateProductName(product.getName())).thenReturn(Mono.empty());
        when(branchValidations.validateExistsBranchById(product.getBranchId())).thenReturn(Mono.empty());
        when(productPersistencePort.addProduct(product)).thenReturn(Mono.just(product));
        when(productPersistencePort.saveProductBranch(product.getId(), product)).thenReturn(Mono.empty());

        Mono<Void> result = productUseCase.addNewProduct(product);

        StepVerifier.create(result).verifyComplete();

        verify(productValidations, times(1)).validateProductName(product.getName());
        verify(branchValidations, times(1)).validateExistsBranchById(product.getBranchId());
        verify(productPersistencePort, times(1)).addProduct(product);
        verify(productPersistencePort, times(1)).saveProductBranch(product.getId(), product);
    }

    @Test
    void addNewProduct_ShouldDeleteProduct_WhenSaveProductBranchFails() {
        when(productValidations.validateProductName(product.getName())).thenReturn(Mono.empty());
        when(branchValidations.validateExistsBranchById(product.getBranchId())).thenReturn(Mono.empty());
        when(productPersistencePort.addProduct(product)).thenReturn(Mono.just(product));
        when(productPersistencePort.saveProductBranch(product.getId(), product)).thenReturn(Mono.error(new RuntimeException("Error")));
        when(productPersistencePort.deleteProductById(product.getId())).thenReturn(Mono.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productUseCase.addNewProduct(product).block());
        assertEquals("Error", exception.getMessage());

        verify(productPersistencePort, times(1)).deleteProductById(product.getId());
    }

    @Test
    void deleteProduct_shouldDeleteProductAndBranchTest() {
        when(productPersistencePort.deleteProductById(product.getId())).thenReturn(Mono.empty());
        when(productPersistencePort.deleteProductBranchByIds(product.getId(), product.getBranchId())).thenReturn(Mono.empty());

        Mono<Void> result = productUseCase.deleteProduct(product.getId(), product.getBranchId());

        StepVerifier.create(result).verifyComplete();

        verify(productPersistencePort, times(1)).deleteProductById(product.getId());
        verify(productPersistencePort, times(1)).deleteProductBranchByIds(product.getId(), product.getBranchId());
    }

    @Test
    void getProducts_shouldReturnProductStockTest() {
        when(franchiseValidations.validateFranchiseExistsById(1L)).thenReturn(Mono.empty());
        when(productPersistencePort.getProductStockByFranchiseId(1L)).thenReturn(Mono.just(productStockByFranchise));

        Mono<ProductStockByFranchise> result = productUseCase.getProducst(1L);

        StepVerifier.create(result)
                .expectNext(productStockByFranchise)
                .verifyComplete();

        verify(franchiseValidations, times(1)).validateFranchiseExistsById(1L);
        verify(productPersistencePort, times(1)).getProductStockByFranchiseId(1L);
    }

    @Test
    void updateStock_ShouldUpdateProductStock() {
        when(productValidations.findProductBrach(productBranch)).thenReturn(Mono.just(productBranch));
        when(productPersistencePort.updateProduct(productBranch)).thenReturn(Mono.just(productBranch));
        Mono<ProductBranch> result = productUseCase.updateStock(productBranch);

        StepVerifier.create(result)
                .expectNext(productBranch)
                .verifyComplete();


        verify(productValidations, times(1)).findProductBrach(productBranch);
        verify(productPersistencePort, times(1)).updateProduct(productBranch);
    }

    @Test
    void updateProductName_ShouldUpdateName_WhenProductExistsAndNameIsValid() {
        Long productId = 1L;
        String newName = "Updated Product";
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Old Product");

        when(productValidations.findProduct(productId)).thenReturn(Mono.just(mockProduct));

        when(productValidations.validateProductName(newName)).thenReturn(Mono.empty());

        when(productPersistencePort.addProduct(mockProduct)).thenReturn(Mono.just(mockProduct));

        Mono<Void> result = productUseCase.updateProductName(productId, newName);

        StepVerifier.create(result).verifyComplete();

        assertEquals(newName, mockProduct.getName());

        verify(productValidations, times(1)).findProduct(productId);
        verify(productValidations, times(1)).validateProductName(newName);
        verify(productPersistencePort, times(1)).addProduct(mockProduct);
    }
}
