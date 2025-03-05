package com.example.franquicias.franquicias_prueba.infrastructure.out.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("Product")
public class ProductEntity {
    @Id
    @Column("id")
    private String id;

    @Column("name")
    private String name;

    @Column("quantityInStock")
    private String quantityInStock;

    @Column("branchId")
    private String branchId;
}
