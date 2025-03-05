package com.example.franquicias.franquicias_prueba.infrastructure.out.repository;

import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.BranchEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface IBranchRepository extends R2dbcRepository<BranchEntity, Long> {
    Mono<Boolean> existsBranchByName(String name);
}
