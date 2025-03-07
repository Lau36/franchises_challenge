package com.example.franquicias.franquicias_prueba.infrastructure.in.routes;

import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.FranchiseRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.FranchiseHandler;
import com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class FranchiseRoute {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = InfraConstans.ADD_FRANCHISE_PATH,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "createFranchise",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "createFranchise",
                            summary = "Create a new franchise",
                            description = "Creates a new franchise",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = FranchiseRequest.class))),
                            responses = {
                                    @ApiResponse(
                                            responseCode = InfraConstans.STATUS_CODE_201,
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(
                                            responseCode = InfraConstans.STATUS_CODE_409,
                                            description = InfraConstans.DATA_NOT_FOUND,
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    )
                                    ,
                                    @ApiResponse(
                                            responseCode = InfraConstans.STATUS_CODE_400,
                                            description = InfraConstans.INVALID_INPUT,
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(
                                            responseCode = InfraConstans.STATUS_CODE_500,
                                            description = InfraConstans.SERVER_ERROR,
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    )
                            }
                    )
            )
    })

    public RouterFunction<ServerResponse> franchisesRoutes(FranchiseHandler franchiseHandler) {
        return RouterFunctions.route(POST(InfraConstans.ADD_FRANCHISE_PATH), franchiseHandler::createFranchise)
                .andRoute(PATCH(InfraConstans.UPDATE_FRANCHISE_PATH), franchiseHandler::updateFranchise);
    }
}
