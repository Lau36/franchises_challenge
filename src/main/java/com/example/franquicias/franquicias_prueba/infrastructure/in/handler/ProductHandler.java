package com.example.franquicias.franquicias_prueba.infrastructure.in.handler;

import com.example.franquicias.franquicias_prueba.aplication.IFranchiseRest;
import com.example.franquicias.franquicias_prueba.aplication.IProductRest;
import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.domain.models.ProductBranch;
import com.example.franquicias.franquicias_prueba.domain.utils.ProductStockByFranchise;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.BranchRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.NameRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductBranchRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.response.ProductsTopStockResponse;
import com.example.franquicias.franquicias_prueba.infrastructure.in.execptions.InvalidDataException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.*;

@RequiredArgsConstructor
@Component
public class ProductHandler {

    private final IProductRest productRest;
    private final IFranchiseRest franchiseRest;

    public Mono<ServerResponse> addNewProduct(ServerRequest serverRequest) {
        Product product = new Product();

        return serverRequest.bodyToMono(ProductRequest.class)
                .switchIfEmpty(Mono.error(new InvalidDataException(PRODUCT_INFO_REQUIRED)))
                .map(productRequest ->
                        {
                            product.setName(productRequest.getName());
                            product.setBranchId((long) productRequest.getBranchId());
                            product.setStock(productRequest.getStock());
                            return product;
                        }
                        )
                .flatMap(productRest::addNewProduct)
                .then(ServerResponse.status(HttpStatus.CREATED).build())
                .onErrorResume(NotFoundException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(AlreadyExistsException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(InvalidDataException.class, ex ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                .onErrorResume( ex ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(SERVER_ERROR))
                ;
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest serverRequest) {

        return Mono.defer( () -> {
                    Long productId = Long.valueOf(serverRequest.queryParam(PRODUCT_ID).orElseThrow(
                            () -> new InvalidDataException(PRODUCT_ID_REQUIRED)
                    ));

                    Long branchId = Long.valueOf(serverRequest.queryParam(BRANCH_ID).orElseThrow(
                            () -> new InvalidDataException(BRANCH_ID_REQUIRED)
                    ));
                    return productRest.deleteProduct(productId, branchId);

                })
                .then(ServerResponse.ok().build())
                .onErrorResume(NotFoundException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(InvalidDataException.class, ex ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                .onErrorResume( ex ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(SERVER_ERROR))
                ;
    }

    public Mono<ServerResponse> getProducts(ServerRequest serverRequest) {

        return Mono.defer( () -> {

                    Long franchiseId = Long.valueOf(serverRequest.queryParam(FRANCHISE_ID).orElseThrow(
                            () -> new InvalidDataException(FRANCHISE_ID_REQUIRED)
                    ));

                    return productRest.getAllProductStockByFranchise(franchiseId)
                            .map(products -> ProductsTopStockResponse.builder()
                                    .franchiseId(franchiseId)
                                    .products(products.getProducts())
                                    .build());
                })
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .onErrorResume(NotFoundException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(AlreadyExistsException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(InvalidDataException.class, ex ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                .onErrorResume( ex ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(SERVER_ERROR))
                ;
    }

    public Mono<ServerResponse> updateStockProduct(ServerRequest serverRequest) {
        ProductBranch product = new ProductBranch();
        return serverRequest.bodyToMono(ProductBranchRequest.class)
                .switchIfEmpty(Mono.error(new InvalidDataException(PRODUCT_INFO_REQUIRED)))
                .map(productRequest ->
                        {
                            product.setProductId(productRequest.getProductId());
                            product.setBranchId(productRequest.getBranchId());
                            product.setStock(productRequest.getStock());
                            return product;
                        }
                )
                .flatMap(productRest::updateStock)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .onErrorResume(NotFoundException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(InvalidDataException.class, ex ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                .onErrorResume( ex ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(SERVER_ERROR))
                ;
    }

    public Mono<ServerResponse> updateProductName(ServerRequest serverRequest) {
        return Mono.defer( () -> {
                    Long productId = Long.valueOf(serverRequest.queryParam(PRODUCT_ID).orElseThrow(
                            () -> new InvalidDataException(PRODUCT_ID_REQUIRED )
                    ));
                    return serverRequest.bodyToMono(NameRequest.class)
                            .switchIfEmpty(Mono.error(new InvalidDataException(NAME_REQUIRED)))
                            .flatMap(
                                    request -> productRest.updateProductName(productId, request.getNewName())
                                            .thenReturn(request.getNewName())
                            );

                })
                .flatMap(productName -> ServerResponse.ok().bodyValue(String.format(PRODUCT_NAME_UPDATED,productName)))
                .onErrorResume(NotFoundException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(AlreadyExistsException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(InvalidDataException.class, ex ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                .onErrorResume( ex ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(SERVER_ERROR))
                ;
    }

}
