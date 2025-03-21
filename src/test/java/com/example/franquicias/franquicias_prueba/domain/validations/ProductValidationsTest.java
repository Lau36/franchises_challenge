package com.example.franquicias.franquicias_prueba.domain.validations;

import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IProductPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.ProductValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.PRODUCT_NOT_FOUND;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductValidationsTest {
    @Mock
    private IProductPersistencePort productPersistencePort;

    @InjectMocks
    private ProductValidations productValidations;

    private ProductBranch productBranch;

    @BeforeEach
    void setUp() {
        productBranch = new ProductBranch();
        productBranch.setId(1L);
        productBranch.setBranchId(3L);
        productBranch.setProductId(2L);
    }

    @Test
    public void validateProductName_shouldReturnOkTest() {
        Mockito.when(productPersistencePort.existsProductByName("Test Product")).thenReturn(Mono.just(false));

        Mono<Void> result = productValidations.validateProductName("Test Product");

        StepVerifier.create(result).verifyComplete();

        verify(productPersistencePort, times(1)).existsProductByName("Test Product");
    }

    @Test
    public void validateProductName_shouldReturnErrorTest() {
        Mockito.when(productPersistencePort.existsProductByName("Test Product")).thenReturn(Mono.just(true));

        Mono<Void> result = productValidations.validateProductName("Test Product");

        StepVerifier.create(result)
                .expectError(AlreadyExistsException.class)
                .verify();

        verify(productPersistencePort, times(1)).existsProductByName("Test Product");
    }

    @Test
    public void findProduct_shouldReturnProductBrachTest() {

        Mockito.when(productPersistencePort.findProductBranchByIds(2L, 3L)).thenReturn(Mono.just(productBranch));

        Mono<ProductBranch> result = productValidations.findProductBrach(productBranch);

        StepVerifier.create(result)
                .expectNext(productBranch)
                .verifyComplete();

        verify(productPersistencePort, times(1)).findProductBranchByIds(2L, 3L);
    }

    @Test
    public void findProduct_Brach_shouldReturnErrorTest() {

        Mockito.when(productPersistencePort.findProductBranchByIds(2L, 3L)).thenReturn(Mono.empty());

        Mono<ProductBranch> result = productValidations.findProductBrach(productBranch);

        StepVerifier.create(result)
                .expectError(NotFoundException.class)
                .verify();

        verify(productPersistencePort, times(1)).findProductBranchByIds(2L, 3L);
    }

    @Test
    void findProduct_shouldReturnProduct_whenProductExistsTest() {
        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Test Product");

        Mockito.when(productPersistencePort.findProductById(productId))
                .thenReturn(Mono.just(mockProduct));

        StepVerifier.create(productValidations.findProduct(productId))
                .expectNextMatches(product -> product.getId().equals(productId) &&
                        product.getName().equals("Test Product"))
                .verifyComplete();
    }

    @Test
    void findProduct_shouldThrowNotFoundException_whenProductDoesNotExistTest() {

        Long productId = 2L;
        Mockito.when(productPersistencePort.findProductById(productId))
                .thenReturn(Mono.empty());

        StepVerifier.create(productValidations.findProduct(productId))
                .expectErrorMatches(throwable -> throwable instanceof NotFoundException &&
                        throwable.getMessage().contains(String.format(PRODUCT_NOT_FOUND, productId)))
                .verify();
    }
}
