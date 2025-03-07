package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseRest {
    Mono<Void> createFranchise(Franchise franchise);
    Mono<Void> updateFranchise(Long id, String name);
}
