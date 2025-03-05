package com.example.franquicias.franquicias_prueba.infrastructure.out.repository;

import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.ProductBranchEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface IProductBranchRepository extends R2dbcRepository<ProductBranchEntity, Long> {
    Mono<Void> deleteByProductIdAndBranchId(Long productId, Long branchId);

}
