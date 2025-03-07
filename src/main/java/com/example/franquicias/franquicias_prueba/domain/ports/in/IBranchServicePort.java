package com.example.franquicias.franquicias_prueba.domain.ports.in;

import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import reactor.core.publisher.Mono;

public interface IBranchServicePort {
    Mono<Void> addBranch(Branch branch);
    Mono<Void> updateBranchName(Long id, String name);

}
