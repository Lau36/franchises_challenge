package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IBranchServicePort;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IBranchPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import reactor.core.publisher.Mono;

public class BranchUseCase implements IBranchServicePort {

    private final IBranchPersistencePort branchPersistencePort;
    private final FranchiseValidations franchiseValidations;

    public BranchUseCase(IBranchPersistencePort branchPersistencePort, FranchiseValidations franchiseValidations) {
        this.branchPersistencePort = branchPersistencePort;
        this.franchiseValidations = franchiseValidations;
    }

    @Override
    public Mono<Void> addBranch(Branch branch) {
        return franchiseValidations.validateFranchiseExistsById(branch.getFranchiseId())
                .then(branchPersistencePort.saveFranchise(branch));
    }
}
