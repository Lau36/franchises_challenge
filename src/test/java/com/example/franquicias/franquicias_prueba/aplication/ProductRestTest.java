package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.aplication.impl.ProductRest;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IProductServicePort;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStock;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductBranchRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.response.ProductStockResponse;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.response.ProductsTopStockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductRestTest {

    @Mock
    private IProductServicePort productServicePort;

    @InjectMocks
    private ProductRest productRest;

    private ProductRequest productRequest;
    private ProductBranch productBranch;
    private ProductBranchRequest productBranchRequest;
    private ProductsTopStockResponse productsTopStockResponse;
    private ProductStockByFranchise productStockByFranchise;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest();
        productRequest.setBranchId(1);
        productRequest.setName("Producto A");

        productBranch = new ProductBranch();
        productBranch.setBranchId(1L);
        productBranch.setProductId(1L);
        productBranch.setStock(100);

        productsTopStockResponse = new ProductsTopStockResponse();
        productStockByFranchise = new ProductStockByFranchise();

        productStockByFranchise.setProducts(List.of(
                new ProductStock("Producto 1", "Sucursal 1", 10)));

        ProductStockResponse productStockResponse = new ProductStockResponse();
        productStockResponse.setName("Producto 1");
        productStockResponse.setStock(10);
        productStockResponse.setBranchName("Sucursal 1");

        productsTopStockResponse.setProducts(List.of(productStockResponse));

        productBranchRequest = new ProductBranchRequest();
        productBranchRequest.setBranchId(1L);
        productBranchRequest.setProductId(1L);
        productBranchRequest.setStock(10);
    }

    @Test
    void
    testAddNewProduct_Success() {
        when(productServicePort.addNewProduct(any(Product.class))).thenReturn(Mono.empty());

        Mono<Void> result = productRest.addNewProduct(productRequest);

        StepVerifier.create(result)
                .verifyComplete();

        verify(productServicePort, times(1)).addNewProduct(any(Product.class));
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
                .expectNextMatches(response ->
                        response.getFranchiseId().equals(franchiseId) &&
                                response.getProducts().size() == productsTopStockResponse.getProducts().size() &&
                                response.getProducts().get(0).getName().equals(productsTopStockResponse.getProducts().get(0).getName()) &&
                                response.getProducts().get(0).getBranchName().equals(productsTopStockResponse.getProducts().get(0).getBranchName()) &&
                                Objects.equals(response.getProducts().get(0).getStock(), productsTopStockResponse.getProducts().get(0).getStock())
                )
                .verifyComplete();


        verify(productServicePort, times(1)).getProducst(franchiseId);
    }

    @Test
    void testUpdateStock_Success() {
        when(productServicePort.updateStock(any(ProductBranch.class))).thenReturn(Mono.just(productBranch));

        StepVerifier.create(productRest.updateStock(productBranchRequest))
                .verifyComplete();

        verify(productServicePort, times(1)).updateStock(any(ProductBranch.class));
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
