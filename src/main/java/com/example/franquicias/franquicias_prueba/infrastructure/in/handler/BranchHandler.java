package com.example.franquicias.franquicias_prueba.infrastructure.in.handler;

import com.example.franquicias.franquicias_prueba.aplication.IBranchRest;
import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.BranchRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.NameRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.execptions.InvalidDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.*;

@RequiredArgsConstructor
@Component
public class BranchHandler {
    private final IBranchRest branchRest;

    public Mono<ServerResponse> addBranch(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(BranchRequest.class)
                .switchIfEmpty(Mono.error(new InvalidDataException(BRANCH_INFO_REQUIRED)))
                .flatMap(branchRest::addBranch)
                .then(ServerResponse.ok().build())
                .onErrorResume(NotFoundException.class, ex ->
                        ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(ex.getMessage()))
                .onErrorResume(AlreadyExistsException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(InvalidDataException.class, ex ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                .onErrorResume( ex ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(SERVER_ERROR))
                ;
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest serverRequest) {

        return Mono.defer( () -> {
                    Long branchId = Long.valueOf(serverRequest.queryParam(BRANCH_ID).orElseThrow(
                            () -> new InvalidDataException(BRANCH_ID_REQUIRED )
                    ));

                    return serverRequest.bodyToMono(NameRequest.class)
                            .switchIfEmpty(Mono.error(new InvalidDataException(NAME_REQUIRED)))
                            .flatMap(
                                    request -> branchRest.updateBranchName(branchId, request.getNewName())
                                            .thenReturn(request.getNewName())
                            );

                })
                .flatMap(name -> ServerResponse.ok().bodyValue(String.format(BRANCH_NAME_UPDATED, name)))
                .onErrorResume(NotFoundException.class, ex ->
                        ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(ex.getMessage()))
                .onErrorResume(AlreadyExistsException.class, ex ->
                        ServerResponse.status(HttpStatus.CONFLICT).bodyValue(ex.getMessage()))
                .onErrorResume(InvalidDataException.class, ex ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(ex.getMessage()))
                .onErrorResume( ex ->
                        ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(SERVER_ERROR))
                ;
    }
}

