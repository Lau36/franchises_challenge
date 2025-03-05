package com.example.franquicias.franquicias_prueba.infrastructure.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductStockDTO {
    private String name;
    private String branchName;
    private Integer stock;
}
