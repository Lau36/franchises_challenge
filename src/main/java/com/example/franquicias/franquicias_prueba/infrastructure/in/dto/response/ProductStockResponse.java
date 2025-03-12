package com.example.franquicias.franquicias_prueba.infrastructure.in.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductStockResponse {
    private String name;
    private String branchName;
    private Integer stock;
}
