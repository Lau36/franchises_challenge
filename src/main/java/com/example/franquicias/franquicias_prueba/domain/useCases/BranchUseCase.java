package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IBranchServicePort;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IBranchPersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.BranchValidations;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import reactor.core.publisher.Mono;

public class BranchUseCase implements IBranchServicePort {

    private final IBranchPersistencePort branchPersistencePort;
    private final FranchiseValidations franchiseValidations;
    private final BranchValidations branchValidations;

    public BranchUseCase(IBranchPersistencePort branchPersistencePort, FranchiseValidations franchiseValidations, BranchValidations branchValidations) {
        this.branchPersistencePort = branchPersistencePort;
        this.franchiseValidations = franchiseValidations;
        this.branchValidations = branchValidations;
    }

    @Override
    public Mono<Void> addBranch(Branch branch) {
        return Mono.when(
                        franchiseValidations.validateFranchiseExistsById(branch.getFranchiseId()),
                        branchValidations.validateExistsBranchByName(branch.getName())
                )
                .then(branchPersistencePort.saveFranchise(branch));
    }

    @Override
    public Mono<Branch> updateBranchName(Long id, String name) {
        return branchValidations.validateBranchId(id)
                .flatMap(
                        existingBranch ->
                            branchValidations.validateExistsBranchByName(name)
                                    .then(
                                    Mono.defer(() -> {
                                        existingBranch.setName(name);
                                        return branchPersistencePort.saveFranchise(existingBranch)
                                                .thenReturn(existingBranch);
                                    })
                            )

                );
    }
}
