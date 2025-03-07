package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.aplication.impl.ProductRest;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IProductServicePort;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductRestTest {

    @Mock
    private IProductServicePort productServicePort;

    @InjectMocks
    private ProductRest productRest;

    private Product product;
    private ProductBranch productBranch;
    private ProductStockByFranchise productStockByFranchise;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Producto A");

        productBranch = new ProductBranch();
        productBranch.setBranchId(1L);
        productBranch.setProductId(1L);
        productBranch.setStock(100);

        productStockByFranchise = new ProductStockByFranchise();
    }

    @Test
    void testAddNewProduct_Success() {
        when(productServicePort.addNewProduct(product)).thenReturn(Mono.empty());

        Mono<Void> result = productRest.addNewProduct(product);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productServicePort, times(1)).addNewProduct(product);
    }

    @Test
    void testDeleteProduct_Success() {
        Long productId = 1L;
        Long branchId = 2L;

        when(productServicePort.deleteProduct(productId, branchId)).thenReturn(Mono.empty());

        StepVerifier.create(productRest.deleteProduct(productId, branchId))
                .verifyComplete();

        verify(productServicePort, times(1)).deleteProduct(productId, branchId);
    }

    @Test
    void testGetAllProductStockByFranchise_Success() {
        Long franchiseId = 1L;

        when(productServicePort.getProducst(franchiseId)).thenReturn(Mono.just(productStockByFranchise));

        StepVerifier.create(productRest.getAllProductStockByFranchise(franchiseId))
                .expectNext(productStockByFranchise)
                .verifyComplete();

        verify(productServicePort, times(1)).getProducst(franchiseId);
    }

    @Test
    void testUpdateStock_Success() {
        when(productServicePort.updateStock(productBranch)).thenReturn(Mono.just(productBranch));

        StepVerifier.create(productRest.updateStock(productBranch))
                .expectNext(productBranch)
                .verifyComplete();

        verify(productServicePort, times(1)).updateStock(productBranch);
    }

    @Test
    void testUpdateProductName_Success() {
        Long productId = 1L;
        String newName = "Producto B";

        when(productServicePort.updateProductName(productId, newName)).thenReturn(Mono.empty());

        StepVerifier.create(productRest.updateProductName(productId, newName))
                .verifyComplete();

        verify(productServicePort, times(1)).updateProductName(productId, newName);
    }
}
