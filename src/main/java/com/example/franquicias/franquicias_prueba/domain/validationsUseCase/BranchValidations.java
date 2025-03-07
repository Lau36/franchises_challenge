package com.example.franquicias.franquicias_prueba.domain.validationsUseCase;

import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IBranchPersistencePort;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.ALREADY_EXIST_BRANCH;
import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.BRANCH_NOT_FOUND;

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

    public Mono<Void> validateExistsBranchById(Long branchId) {
        return branchPersistencePort.existsBranchById(branchId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(BRANCH_NOT_FOUND, branchId))))
                .then();
    }

    public Mono<Branch> validateBranchId(Long branchId) {
        return branchPersistencePort.findBranchById(branchId)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format(BRANCH_NOT_FOUND, branchId))));
    }
}
