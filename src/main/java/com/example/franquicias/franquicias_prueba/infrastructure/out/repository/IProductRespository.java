package com.example.franquicias.franquicias_prueba.infrastructure.out.repository;

import com.example.franquicias.franquicias_prueba.infrastructure.out.dto.ProductStockDTO;
import com.example.franquicias.franquicias_prueba.infrastructure.out.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.QUERY_TO_GET_TOP_STOCKED_PRODUCTS_BY_FRANCHISE_ID;

public interface IProductRespository extends R2dbcRepository<ProductEntity, Long> {
    Mono<Boolean> existsProductByName(String productName);
    Mono<Void> deleteById(Long productId);

    @Query("""
           SELECT p.name, pb.stock, b.name AS branch_name
           FROM product_branch pb
           JOIN product p ON pb.product_id = p.id
           JOIN branch b ON pb.branch_id = b.id
           WHERE pb.stock = (
               SELECT MAX(stock) FROM product_branch WHERE branch_id = b.id
           )
           AND b.franchise_id = :franchiseId
           """)
    Flux<ProductStockDTO> findTopStockedProductsByFranchiseId(Long franchiseId);
}
