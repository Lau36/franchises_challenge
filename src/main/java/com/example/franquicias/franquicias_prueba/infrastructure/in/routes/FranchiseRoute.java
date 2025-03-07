package com.example.franquicias.franquicias_prueba.infrastructure.in.routes;

import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.FranchiseRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.NameRequest;
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

import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.FRANCHISE_ID;
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
                            summary = "Crear una nueva franquicia",
                            description = "Crea una nueva franquicia",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = FranchiseRequest.class))),
                            responses = {
                                    @ApiResponse(
                                            responseCode = InfraConstans.STATUS_CODE_201,
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_409,
                                            description = "El nombre de la franquicia ya existe",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(
                                            responseCode = InfraConstans.STATUS_CODE_400,
                                            description = "Entrada inválida",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(
                                            responseCode = InfraConstans.STATUS_CODE_500,
                                            description = "Error interno del servidor",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    )
                            }
                    )
            ),

            @RouterOperation(
                    path = InfraConstans.UPDATE_FRANCHISE_PATH,
                    beanClass = FranchiseHandler.class,
                    beanMethod = "updateFranchise",
                    method = RequestMethod.PATCH,
                    operation = @Operation(
                            operationId = "updateFranchiseName",
                            summary = "Actualizar el nombre de la franquicia",
                            description = "Actualiza el nombre de una franquicia existente",
                            parameters = {
                                    @Parameter(name = FRANCHISE_ID, description = "ID de la franquicia", required = true),
                            },
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = NameRequest.class))),
                            responses = {
                                    @ApiResponse(
                                            responseCode = InfraConstans.STATUS_CODE_201,
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(
                                            responseCode = InfraConstans.STATUS_CODE_404,
                                            description = "Datos no encontrados",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(
                                            responseCode = InfraConstans.STATUS_CODE_400,
                                            description = "Entrada inválida",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_409,
                                            description = "El nombre de la franquicia ya existe",
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
