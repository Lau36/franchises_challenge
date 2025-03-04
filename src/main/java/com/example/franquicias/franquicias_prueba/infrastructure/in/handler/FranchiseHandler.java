package com.example.franquicias.franquicias_prueba.infrastructure.in.handler;

import com.example.franquicias.franquicias_prueba.aplication.IFranchiseRest;
import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.FranchiseRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.execptions.InvalidDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.FRANCHISE_INFO_REQUIRED;
import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.SERVER_ERROR;

@RequiredArgsConstructor
@Component
public class FranchiseHandler {

    private final IFranchiseRest franchiseRest;

    public Mono<ServerResponse> createFranchise(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FranchiseRequest.class)
                .switchIfEmpty(Mono.error(new InvalidDataException(FRANCHISE_INFO_REQUIRED)))
                .map(franchiseRequest -> new Franchise(null, franchiseRequest.getName()))
                .flatMap(franchiseRest::createFranchise)
                .then(ServerResponse.status(HttpStatus.CREATED).build())
                .onErrorResume(AlreadyExistsException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(InvalidDataException.class, ex ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                .onErrorResume( ex ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(SERVER_ERROR))
                ;
    }
}
