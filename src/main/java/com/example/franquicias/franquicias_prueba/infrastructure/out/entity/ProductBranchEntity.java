package com.example.franquicias.franquicias_prueba.infrastructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("product_branch")
@Builder
public class ProductBranchEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("stock")
    private Integer stock;

    @Column("product_id")
    private Long productId;

    @Column("branch_id")
    private Long branchId;
}
