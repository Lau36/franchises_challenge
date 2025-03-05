package com.example.franquicias.franquicias_prueba.infrastructure.in.routes;

import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.FranchiseHandler;
import com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class FranchiseRoute {

    @Bean
    public RouterFunction<ServerResponse> franchisesRoutes(FranchiseHandler franchiseHandler) {
        return RouterFunctions.route(POST(InfraConstans.ADD_FRANCHISE_PATH), franchiseHandler::createFranchise);
    }
}
