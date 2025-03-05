package com.example.franquicias.franquicias_prueba.domain.validationsUseCase;

import com.example.franquicias.franquicias_prueba.domain.exceptions.AlreadyExistsException;
import com.example.franquicias.franquicias_prueba.domain.exceptions.NotFoundException;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IFranchisePersistencePort;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.ALREADY_EXIST_FRANCHISE;
import static com.example.franquicias.franquicias_prueba.domain.utils.constans.DomainConstans.FRANCHISE_NOT_FOUND;


public class FranchiseValidations {

    private final IFranchisePersistencePort franchisePersistencePort;

    public FranchiseValidations(IFranchisePersistencePort franchisePersistencePort) {
        this.franchisePersistencePort = franchisePersistencePort;
    }

    public Mono<Void> validateFranchiseName(Franchise franchise) {

        return franchisePersistencePort.existsFranchise(franchise.getName())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new AlreadyExistsException(String.format(ALREADY_EXIST_FRANCHISE, franchise.getName()))))
                .then();
    }

    public Mono<Void> validateFranchiseExistsById(Long id) {

        return franchisePersistencePort.existsFranchiseById(id)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new NotFoundException(FRANCHISE_NOT_FOUND)))
                .then();
    }
}
