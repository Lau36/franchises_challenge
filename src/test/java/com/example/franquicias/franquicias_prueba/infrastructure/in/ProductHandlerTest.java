package com.example.franquicias.franquicias_prueba.infrastructure.in;

import com.example.franquicias.franquicias_prueba.aplication.IProductRest;
import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductBranchRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.ProductHandler;
import com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URI;
import java.util.Optional;

import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductHandlerTest {

    @Mock
    private IProductRest productRest;

    @InjectMocks
    private ProductHandler productHandler;

    private ProductRequest productRequest;
    private ProductBranchRequest productBranchRequest;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest("Product Test", 1, 10);
        productBranchRequest = new ProductBranchRequest(1L, 1L, 20);
    }

    @Test
    public void addNewProduct_ShouldReturnCreatedStatus() {
        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create(ADD_PRODUCT_PATH))
                .body(Mono.just(productRequest));

        when(productRest.addNewProduct(any(Product.class))).thenReturn(Mono.empty());

        StepVerifier.create(productHandler.addNewProduct(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CREATED))
                .verifyComplete();

        verify(productRest, times(1)).addNewProduct(any(Product.class));
    }

    @Test
    public void deleteProduct_ShouldReturnOkStatus() {
        ServerRequest request = mock(ServerRequest.class);

        when(request.queryParam("productId")).thenReturn(Optional.of("1"));
        when(request.queryParam("branchId")).thenReturn(Optional.of("1"));
        when(productRest.deleteProduct(1L, 1L)).thenReturn(Mono.empty());

        StepVerifier.create(productHandler.deleteProduct(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(productRest, times(1)).deleteProduct(1L, 1L);
    }


    @Test
    public void getProducts_ShouldReturnOkStatus() {
        ServerRequest request = mock(ServerRequest.class);

        when(request.queryParam("franchiseId")).thenReturn(Optional.of("1"));
        when(productRest.getAllProductStockByFranchise(1L)).thenReturn(Mono.just(new ProductStockByFranchise()));

        StepVerifier.create(productHandler.getProducts(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(productRest, times(1)).getAllProductStockByFranchise(1L);
    }

    @Test
    public void updateStockProduct_ShouldReturnOkStatus() {
        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.PUT)
                .uri(URI.create(UPDATE_STOCK_PATH))
                .body(Mono.just(productBranchRequest));

        when(productRest.updateStock(any(ProductBranch.class))).thenReturn(Mono.just(new ProductBranch()));

        StepVerifier.create(productHandler.updateStockProduct(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.OK))
                .verifyComplete();

        verify(productRest, times(1)).updateStock(any(ProductBranch.class));
    }

    @Test
    public void addNewProduct_ShouldReturnConflict_WhenAlreadyExists() {
        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.POST)
                .uri(URI.create(ADD_PRODUCT_PATH))
                .body(Mono.just(productRequest));

        when(productRest.addNewProduct(any(Product.class))).thenReturn(Mono.error(new AlreadyExistsException("Product already exists")));

        StepVerifier.create(productHandler.addNewProduct(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CONFLICT))
                .verifyComplete();
    }

    @Test
    public void deleteProduct_ShouldReturnBadRequest_WhenMissingSomeParams() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.queryParam("productId")).thenReturn(Optional.empty());

        StepVerifier.create(productHandler.deleteProduct(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.BAD_REQUEST))
                .verifyComplete();
    }

    @Test
    public void getProducts_ShouldReturnConflict_WhenFranchiseNotFound() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.queryParam("franchiseId")).thenReturn(Optional.of("1"));
        when(productRest.getAllProductStockByFranchise(1L)).thenReturn(Mono.error(new NotFoundException("Franchise not found")));

        StepVerifier.create(productHandler.getProducts(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.CONFLICT))
                .verifyComplete();
    }

    @Test
    public void updateStockProduct_ShouldReturnServerError_WhenUnexpectedError() {
        ServerRequest request = MockServerRequest.builder()
                .method(HttpMethod.PUT)
                .uri(URI.create(UPDATE_STOCK_PATH))
                .body(Mono.just(productBranchRequest));

        when(productRest.updateStock(any(ProductBranch.class))).thenReturn(Mono.error(new RuntimeException("Error")));

        StepVerifier.create(productHandler.updateStockProduct(request))
                .expectNextMatches(response -> response.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                .verifyComplete();
    }
}
