package com.example.franquicias.franquicias_prueba.domain.ports.in;

import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import reactor.core.publisher.Mono;

public interface IFranchiseServicePort {
    Mono<Void> saveFranchise(Franchise franchise);
}
