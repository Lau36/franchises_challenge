package com.example.franquicias.franquicias_prueba.aplication.impl;

import com.example.franquicias.franquicias_prueba.aplication.IFranchiseRest;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IFranchiseServicePort;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.FranchiseRequest;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class FranchiseRest implements IFranchiseRest {
    private final IFranchiseServicePort franchiseServicePort;

    @Override
    public Mono<Void> createFranchise(FranchiseRequest franchiseRequest) {
        Franchise franchise = new Franchise();
        franchise.setName(franchiseRequest.getName());
        return franchiseServicePort.saveFranchise(franchise);
    }

    @Override
    public Mono<Void> updateFranchise(Long id, String name) {
        return franchiseServicePort.updatFranchiseName(id, name);
    }
}
