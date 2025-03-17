package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.FranchiseRequest;
import reactor.core.publisher.Mono;

public interface IFranchiseRest {
    Mono<Void> createFranchise(FranchiseRequest franchiseRequest);
    Mono<Void> updateFranchise(Long id, String name);
}
