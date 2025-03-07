package com.example.franquicias.franquicias_prueba.infrastructure.in.routes;

import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.BranchRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.NameRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.BranchHandler;
import com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class BranchRoute {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = InfraConstans.ADD_BRANCH_PATH,
                    beanClass = BranchHandler.class,
                    beanMethod = "addBranch",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "addBranch",
                            summary = "Agregar una nueva sucursal",
                            description = "Crea una nueva sucursal dentro de una franquicia",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = BranchRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_200,
                                            description = "Sucursal creada exitosamente",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_404,
                                            description = "Franquicia no encontrada",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_409,
                                            description = "El nombre de la sucursal ya existe",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_400,
                                            description = "Entrada inválida",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_500,
                                            description = "Error interno del servidor",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = InfraConstans.UPDATE_BRANCH_PATH,
                    beanClass = BranchHandler.class,
                    beanMethod = "updateBranchName",
                    method = RequestMethod.PATCH,
                    operation = @Operation(
                            operationId = "updateBranchName",
                            summary = "Actualizar el nombre de la sucursal",
                            description = "Actualiza el nombre de una sucursal existente",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = NameRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_200,
                                            description = "Nombre de la sucursal actualizado exitosamente",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_404,
                                            description = "Sucursal no encontrada",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_409,
                                            description = "El nombre de la sucursal ya existe",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_400,
                                            description = "Entrada inválida",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_500,
                                            description = "Error interno del servidor",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> branchRoutes(BranchHandler branchHandler) {
        return RouterFunctions.route(POST(InfraConstans.ADD_BRANCH_PATH), branchHandler::addBranch)
                .andRoute(PATCH(InfraConstans.UPDATE_BRANCH_PATH), branchHandler::updateBranchName);
    }
}
