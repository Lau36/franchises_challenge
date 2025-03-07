package com.example.franquicias.franquicias_prueba.infrastructure.in.routes;

import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.NameRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.request.ProductRequest;
import com.example.franquicias.franquicias_prueba.infrastructure.in.dto.response.ProductsTopStockResponse;
import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.BranchHandler;
import com.example.franquicias.franquicias_prueba.infrastructure.in.handler.ProductHandler;
import com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.BRANCH_ID;
import static com.example.franquicias.franquicias_prueba.infrastructure.utils.constants.InfraConstans.PRODUCT_ID;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ProductRoute {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = InfraConstans.ADD_PRODUCT_PATH,
                    beanClass = ProductHandler.class,
                    beanMethod = "addNewProduct",
                    method = RequestMethod.POST,
                    operation = @Operation(
                            operationId = "addNewProduct",
                            summary = "Agregar un nuevo producto",
                            description = "Crea un nuevo producto en el inventario",
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = ProductRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_201,
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_404,
                                            description = "Datos no encontrados",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_409,
                                            description = "El dato ya existe",
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
                    path = InfraConstans.DELETE_PRODUCT_PATH,
                    beanClass = ProductHandler.class,
                    beanMethod = "deleteProduct",
                    method = RequestMethod.DELETE,
                    operation = @Operation(
                            operationId = "deleteProduct",
                            summary = "Eliminar un producto",
                            description = "Elimina un producto existente por su ID",
                            parameters = @Parameter(
                                    name = PRODUCT_ID, description = "ID del producto a eliminar", required = true),
                            responses = {
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_200,
                                            description = "Producto eliminado exitosamente"
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_404,
                                            description = "Datos no encontrados",
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
                    path = InfraConstans.GET_PRODUCTS_STOCK_PATH,
                    beanClass = ProductHandler.class,
                    beanMethod = "getProducts",
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "getProducts",
                            summary = "Obtener el stock de productos",
                            description = "Recupera los detalles de stock de los productos disponibles",
                            responses = {
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_200,
                                            content = @Content(
                                                    array =
                                                    @ArraySchema(schema =
                                                    @Schema(implementation = ProductsTopStockResponse.class)))
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_404,
                                            description = "Datos no encontrados",
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_409,
                                            description = "El dato ya existe",
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
                    path = InfraConstans.UPDATE_STOCK_PATH,
                    beanClass = ProductHandler.class,
                    beanMethod = "updateStockProduct",
                    method = RequestMethod.PATCH,
                    operation = @Operation(
                            operationId = "updateStockProduct",
                            summary = "Actualizar el stock del producto",
                            description = "Actualiza el stock de un producto existente",
                            parameters = {
                                    @Parameter(name = PRODUCT_ID, description = "ID del producto", required = true),
                                    @Parameter(name = BRANCH_ID, description = "ID de la sucursal asociada al producto", required = true)
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = ProductRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_200,
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_404,
                                            description = "Datos no encontrados",
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
                    path = InfraConstans.UPDATE_PRODUCT_PATH,
                    beanClass = ProductHandler.class,
                    beanMethod = "updateProductName",
                    method = RequestMethod.PATCH,
                    operation = @Operation(
                            operationId = "updateProductName",
                            summary = "Actualizar el nombre del producto",
                            description = "Actualiza el nombre de un producto existente",
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = NameRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_200,
                                            content = @Content(mediaType = InfraConstans.APPLICATION_JSON)
                                    ),
                                    @ApiResponse(responseCode = InfraConstans.STATUS_CODE_404,
                                            description = "Datos no encontrados",
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
    public RouterFunction<ServerResponse> productRoutes(ProductHandler productHandler) {
        return RouterFunctions.route(POST(InfraConstans.ADD_PRODUCT_PATH), productHandler::addNewProduct)
                .andRoute(DELETE(InfraConstans.DELETE_PRODUCT_PATH), productHandler::deleteProduct)
                .andRoute(GET(InfraConstans.GET_PRODUCTS_STOCK_PATH), productHandler::getProducts)
                .andRoute(PATCH(InfraConstans.UPDATE_STOCK_PATH), productHandler::updateStockProduct)
                .andRoute(PATCH(InfraConstans.UPDATE_PRODUCT_PATH), productHandler::updateProductName);
    }
}
