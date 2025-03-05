package com.example.franquicias.franquicias_prueba.domain.validationsUseCase;

import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IBranchPersistencePort;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.ALREADY_EXIST_BRANCH;

public class BranchValidations {
    private final IBranchPersistencePort branchPersistencePort;


    public BranchValidations(IBranchPersistencePort branchPersistencePort) {
        this.branchPersistencePort = branchPersistencePort;
    }

    public Mono<Void> validateExistsBranchByName(String branchName) {
        return branchPersistencePort.existsBranch(branchName).flatMap(
                branch -> Mono.error(new AlreadyExistsException(String.format(ALREADY_EXIST_BRANCH, branchName, branch.getFranchiseId())))
        ).then();
    }
}
