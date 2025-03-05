package com.example.franquicias.franquicias_prueba.infrastructure.in.routes;

import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.BranchHandler;
import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.ProductHandler;
import com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ProductRoute {

    @Bean
    public RouterFunction<ServerResponse> productRoutes(ProductHandler productHandler) {
        return RouterFunctions.route(POST(InfraConstans.ADD_PRODUCT_PATH), productHandler::addNewProduct)
                .andRoute(DELETE(InfraConstans.DELETE_PRODUCT_PATH), productHandler::deleteProduct)
                .andRoute(GET(InfraConstans.GET_PRODUCTS_STOCK_PATH), productHandler::getProducts)
                .andRoute(PUT(InfraConstans.UPDATE_STOCK_PATH), productHandler::updateStockProduct);
    }
}
