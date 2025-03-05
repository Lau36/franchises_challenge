package com.example.franquicias.franquicias_prueba.domain.ports.out;

import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchisePersistencePort {
    Mono<Void> saveFranchise(Franchise franchise);
    Mono<Boolean> existsFranchise(String franchiseName);
    Mono<Boolean> existsFranchiseById(Long id);
}
