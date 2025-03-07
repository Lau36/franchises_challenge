package com.example.franquicias.franquicias_prueba.domain.useCases;

import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IFranchiseServicePort;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IFranchisePersistencePort;
import com.example.franquicias.franquicias_prueba.domain.validationsUseCase.FranchiseValidations;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.FRANCHISE_NOT_FOUND;

public class FranchiseUseCase implements IFranchiseServicePort {

    private final IFranchisePersistencePort franchisePersistencePort;
    private final FranchiseValidations franchiseValidations;

    public FranchiseUseCase(IFranchisePersistencePort franchisePersistencePort, FranchiseValidations franchiseValidations) {
        this.franchisePersistencePort = franchisePersistencePort;
        this.franchiseValidations = franchiseValidations;
    }

    @Override
    public Mono<Void> saveFranchise(Franchise franchise) {
        return franchiseValidations.validateFranchiseName(franchise.getName())
                .then(franchisePersistencePort.saveFranchise(franchise));
    }

    @Override
    public Mono<Void> updatFranchiseName(Long franchiseId, String name) {
        return franchisePersistencePort.findFranchiseById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException(FRANCHISE_NOT_FOUND )))
                .flatMap(existingFranchise ->
                    franchiseValidations.validateFranchiseName(name)
                            .then(Mono.defer(() ->{
                                existingFranchise.setName(name);
                                return franchisePersistencePort.saveFranchise(existingFranchise);
                            }))
                );
    }
}
