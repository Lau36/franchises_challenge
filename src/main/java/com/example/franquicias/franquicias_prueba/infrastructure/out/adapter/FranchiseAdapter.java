package com.example.franquicias.franquicias_prueba.infrastructure.out.adapter;

import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.domain.ports.out.IFranchisePersistencePort;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.FranchiseEntity;
import com.example.franquicias.franquicias_prueba.infrastructure.out.repository.IFranchiseRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class FranchiseAdapter implements IFranchisePersistencePort {
    private final IFranchiseRepository franchiseRepository;

    @Override
    public Mono<Void> saveFranchise(Franchise franchise) {
        FranchiseEntity entity = FranchiseEntity.builder()
                .name(franchise.getName())
                .id(franchise.getId())
                .build();
        return franchiseRepository.save(entity).then();
    }

    @Override
    public Mono<Boolean> existsFranchise(String franchiseName) {
        return franchiseRepository.existsFranchiseByName(franchiseName);
    }

    @Override
    public Mono<Boolean> existsFranchiseById(Long id) {
        return franchiseRepository.existsFranchiseById(id);
    }

    @Override
    public Mono<Franchise> findFranchiseById(Long id) {
        Franchise franchiseModel = new Franchise();
        return franchiseRepository.findFranchiseById(id).map(
                franchiseEntity -> {
                    franchiseModel.setName(franchiseEntity.getName());
                    franchiseModel.setId(franchiseEntity.getId());
                    return franchiseModel;
                }
        );
    }

//    @Override
//    public Mono<Franchise> updateName(Franchise franchise) {
//        FranchiseEntity entity = FranchiseEntity.builder()
//                .name(franchise.getName())
//                .id(franchise.getId())
//                .build();
//        return franchiseRepository.save(entity);
//    }
}
