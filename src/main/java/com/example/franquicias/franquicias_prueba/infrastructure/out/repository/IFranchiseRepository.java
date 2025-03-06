package com.example.franquicias.franquicias_prueba.infrastructure.out.repository;

import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.FranchiseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface IFranchiseRepository extends R2dbcRepository<FranchiseEntity, Long> {
    Mono<Boolean> existsFranchiseByName(String name);
    Mono<Boolean> existsFranchiseById(Long id);
    Mono<FranchiseEntity> findFranchiseById(Long id);
}
