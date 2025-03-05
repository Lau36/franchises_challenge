package com.example.franquicias.franquicias_prueba.infrastructure.in.routes;

import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.BranchHandler;
import com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class BranchRoute {

    @Bean
    public RouterFunction<ServerResponse> branchRoutes(BranchHandler branchHandler) {
        return RouterFunctions.route(POST(InfraConstans.ADD_BRANCH_PATH), branchHandler::addBranch);
    }
}
