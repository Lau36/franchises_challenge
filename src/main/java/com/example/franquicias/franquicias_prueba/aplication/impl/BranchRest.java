package com.example.franquicias.franquicias_prueba.aplication.impl;

import com.example.franquicias.franquicias_prueba.aplication.IBranchRest;
import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IBranchServicePort;
import com.example.franquicias.franquicias_prueba.domain.ports.in.IFranchiseServicePort;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.BranchRequest;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class BranchRest implements IBranchRest {

    private final IBranchServicePort branchServicePort;

    @Override
    public Mono<Void> addBranch(BranchRequest branchRequest) {
        Branch branch = new Branch();
        branch.setName(branchRequest.getName());
        branch.setFranchiseId((long) branchRequest.getFranchiseId());
        return branchServicePort.addBranch(branch);
    }

    @Override
    public Mono<Void> updateBranchName(Long id, String name) {
        return branchServicePort.updateBranchName(id, name);
    }
}
