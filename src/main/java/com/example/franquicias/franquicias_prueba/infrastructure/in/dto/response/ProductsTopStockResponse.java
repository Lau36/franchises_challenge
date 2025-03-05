package com.example.franquicias.franquicias_prueba.infrastructure.in.dto.response;

import com.example.franquicias.franquicias_prueba.domain.utils.ProductStock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductsTopStockResponse {
    private Long franchiseId;
    private List<ProductStock> products;
}
