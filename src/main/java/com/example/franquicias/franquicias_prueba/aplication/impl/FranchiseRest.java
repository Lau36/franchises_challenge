package com.example.franquicias.franquicias_prueba.aplication.impl;

import com.example.franquicias.franquicias_prueba.aplication.IFranchiseRest;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IFranchiseServicePort;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class FranchiseRest implements IFranchiseRest {
    private final IFranchiseServicePort franchiseServicePort;

    @Override
    public Mono<Void> createFranchise(Franchise franchise) {
        return franchiseServicePort.saveFranchise(franchise);
    }
}
