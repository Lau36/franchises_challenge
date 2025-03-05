package com.example.franquicias.franquicias_prueba.domain.ports.out;

import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import reactor.core.publisher.Mono;

public interface IBranchPersistencePort {
    Mono<Void> saveFranchise(Branch branch);
    Mono<Boolean> existsFranchise(String branchName);
    Mono<Branch> existsBranch(String branchName);
    Mono<Boolean> existsBranchById(Long branchId);
}
