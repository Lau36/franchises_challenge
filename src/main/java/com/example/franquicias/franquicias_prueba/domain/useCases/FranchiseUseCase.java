package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IFranchiseServicePort;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IFranchisePersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

public class FranchiseUseCase implements IFranchiseServicePort {

    private final IFranchisePersistencePort franchisePersistencePort;
    private final FranchiseValidations franchiseValidations;

    public FranchiseUseCase(IFranchisePersistencePort franchisePersistencePort, FranchiseValidations franchiseValidations) {
        this.franchisePersistencePort = franchisePersistencePort;
        this.franchiseValidations = franchiseValidations;
    }

    @Override
    public Mono<Void> saveFranchise(Franchise franchise) {
        return franchiseValidations.validateFranchiseExists(franchise)
                .then(franchisePersistencePort.saveFranchise(franchise));
    }
}
