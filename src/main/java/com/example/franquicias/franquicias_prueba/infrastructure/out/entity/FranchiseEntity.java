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
@Table("franchise")
@Builder
public class FranchiseEntity {
    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;
}
