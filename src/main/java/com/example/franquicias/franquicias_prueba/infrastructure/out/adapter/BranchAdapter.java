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
                .id(branch.getId())
                .build();
        return branchRepository.save(branchEntity).then();
    }

    @Override
    public Mono<Boolean> existsFranchise(String branchName) {
        return branchRepository.existsBranchByName(branchName);
    }

    @Override
    public Mono<Branch> existsBranch(String branchName) {
        Branch branch = new Branch();
        return branchRepository.findBranchByName(branchName)
                .map(branchEntity -> {
                    branch.setId(branchEntity.getId());
                    branch.setName(branchEntity.getName());
                    branch.setFranchiseId(branchEntity.getFranchiseId());
                    return branch;
                });
    }

    @Override
    public Mono<Boolean> existsBranchById(Long branchId) {
        return branchRepository.existsBranchById(branchId);
    }

    @Override
    public Mono<Branch> findBranchById(Long branchId) {
        Branch branch = new Branch();
        return branchRepository.findBranchById(branchId)
                .map(branchEntity -> {
                    branch.setId(branchEntity.getId());
                    branch.setName(branchEntity.getName());
                    branch.setFranchiseId(branchEntity.getFranchiseId());
                    return branch;
                });
    }

}
