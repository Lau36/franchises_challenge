package com.example.franquicias.franquicias_prueba.aplication;

import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import reactor.core.publisher.Mono;

public interface IBranchRest {
    Mono<Void> addBranch(Branch branch);
    Mono<Void> updateBranchName(Long id, String name);
}
