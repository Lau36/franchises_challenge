package com.example.franquicias.franquicias_prueba.infrastructure.in.handler;

import com.example.franquicias.franquicias_prueba.aplication.IFranchiseRest;
import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.FranchiseRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.NameRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.execptions.InvalidDataException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.*;
import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.BRANCH_ID_REQUIRED;

@RequiredArgsConstructor
@Component
public class FranchiseHandler {

    private final IFranchiseRest franchiseRest;


    public Mono<ServerResponse> createFranchise(ServerRequest serverRequest) {
        Franchise franchise = new Franchise();
        return serverRequest.bodyToMono(FranchiseRequest.class)
                .switchIfEmpty(Mono.error(new InvalidDataException(FRANCHISE_INFO_REQUIRED)))
                .map(franchiseRequest -> {
                            franchise.setName(franchiseRequest.getName());
                            return franchise;
                        }
                )
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

    public Mono<ServerResponse> updateFranchise(ServerRequest serverRequest) {
        return Mono.defer( () -> {
                    Long franchiseId = Long.valueOf(serverRequest.queryParam(FRANCHISE_ID).orElseThrow(
                            () -> new InvalidDataException(FRANCHISE_ID_REQUIRED )
                    ));

                    return serverRequest.bodyToMono(NameRequest.class)
                            .switchIfEmpty(Mono.error(new InvalidDataException(NAME_REQUIRED)))
                            .flatMap(
                            request -> franchiseRest.updateFranchise(franchiseId, request.getNewName())
                                    .thenReturn(request.getNewName())
                    );

                })
                .flatMap(name -> ServerResponse.ok().bodyValue(String.format(FRANCHISE_NAME_UPDATED, name)))
                .onErrorResume(AlreadyExistsException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(NotFoundException.class, ex ->
                        ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(ex.getMessage()))
                .onErrorResume(InvalidDataException.class, ex ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                ;
    }
}
