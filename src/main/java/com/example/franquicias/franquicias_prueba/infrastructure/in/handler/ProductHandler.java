package com.example.franquicias.franquicias_prueba.infrastructure.in.handler;

import com.example.franquicias.franquicias_prueba.aplication.IProductRest;
import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Product;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.BranchRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductRequest;
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
                .then(ServerResponse.ok().build())
                .onErrorResume(NotFoundException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(AlreadyExistsException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(InvalidDataException.class, ex ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                ;
    }
}
