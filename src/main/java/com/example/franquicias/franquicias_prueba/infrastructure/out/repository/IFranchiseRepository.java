package com.example.franquicias.franquicias_prueba.infrastructure.out.repository;

import com.example.franquicias.franquicias_prueba.domain.models.Franchise;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.FranchiseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface IFranchiseRepository extends R2dbcRepository<FranchiseEntity, Integer> {
    Mono<Boolean> existsFranchiseByName(String name);
}
