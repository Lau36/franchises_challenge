package com.example.franquicias.franquicias_prueba.infrastructure.out.adapter;

import com.example.franquicias.franquicias_prueba.domain.models.Branch;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IBranchPersistencePort;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.BranchEntity;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IBranchRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class BranchAdapter implements IBranchPersistencePort {
    private final IBranchRepository branchRepository;

    @Override
    public Mono<Void> saveFranchise(Branch branch) {
        BranchEntity branchEntity = BranchEntity.builder()
                .name(branch.getName())
                .franchiseId(branch.getFranchiseId())
                .build();
        return branchRepository.save(branchEntity).then();
    }

    @Override
    public Mono<Boolean> existsFranchise(String branchName) {
        return branchRepository.existsBranchByName(branchName);
    }
}
