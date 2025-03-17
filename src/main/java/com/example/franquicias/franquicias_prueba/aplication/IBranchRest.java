package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.aplication.impl.BranchRest;
import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.BranchRequest;
import reactor.core.publisher.Mono;

public interface IBranchRest {
    Mono<Void> addBranch(BranchRequest branchRequest);
    Mono<Void> updateBranchName(Long id, String name);
}
