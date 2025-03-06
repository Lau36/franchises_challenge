package com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductBranchRequest {
    private Long productId;
    private Long branchId;
    private Integer stock;
}
