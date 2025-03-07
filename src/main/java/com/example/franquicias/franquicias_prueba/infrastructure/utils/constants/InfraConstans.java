package com.example.franquicias.franquicias_prueba.infrastructure.utils.constants;

public class InfraConstans {
    public static final String FRANCHISE_INFO_REQUIRED = "La información de la franquicia que se va a crear es requerida";
    public static final String BRANCH_INFO_REQUIRED = "La información de la sucursal que se va a añadir es requerida";
    public static final String PRODUCT_INFO_REQUIRED = "La información de la sucursal que se va a añadir es requerida";
    public static final String PRODUCT_ID_REQUIRED = "El id del producto es requerido";
    public static final String BRANCH_ID_REQUIRED = "El id de la sucursal es requerido";
    public static final String FRANCHISE_ID_REQUIRED = "El id de la franquicia es requerido";
    public static final String NAME_REQUIRED = "El nuevo nombre es requerido";

    public static final String PRODUCT_NAME_UPDATED = "El nombre del producto quedó correctamente actualizado con el valor de '%s'";
    public static final String BRANCH_NAME_UPDATED = "El nombre de la sucursal quedó correctamente actualizado con el valor de '%s'";
    public static final String FRANCHISE_NAME_UPDATED = "El nombre de la franquicia quedó correctamente actualizado con el valor de '%s'";
    public static final String PRODUCT_STOCK_UPDATED = "Se actualizó el stock del producto correctamente";

    public static final String PRODUCT_ID = "productId";
    public static final String BRANCH_ID = "branchId";
    public static final String FRANCHISE_ID = "franchiseId";

    public static final String ADD_FRANCHISE_PATH = "/api/v1/franchise/add";
    public static final String ADD_BRANCH_PATH = "/api/v1/branch/add";
    public static final String ADD_PRODUCT_PATH = "/api/v1/product/add";
    public static final String UPDATE_FRANCHISE_PATH = "/api/v1/franchise/update";
    public static final String UPDATE_BRANCH_PATH = "/api/v1/branch/update";
    public static final String UPDATE_PRODUCT_PATH = "/api/v1/product/update";
    public static final String UPDATE_STOCK_PATH = "/api/v1/product/update/stock";

    public static final String DELETE_PRODUCT_PATH = "/api/v1/product/delete";
    public static final String GET_PRODUCTS_STOCK_PATH = "/api/v1/product/get";


    //Swagger constans
    public static final String STATUS_CODE_201 = "201";
    public static final String STATUS_CODE_200 = "200";
    public static final String STATUS_CODE_400 = "400";
    public static final String STATUS_CODE_404 = "404";
    public static final String STATUS_CODE_409 = "409";
    public static final String STATUS_CODE_500 = "500";

    public static final String DATA_NOT_FOUND = "Data not found";
    public static final String ALREADY_EXISTS_DATA = "Already exists data";
    public static final String INVALID_INPUT = "Invalid input";
    public static final String SERVER_ERROR = "Ocurrio un error con el servidor";
    public static final String APPLICATION_JSON = "application/json";


    public static final String QUERY_TO_GET_MOST_STOCKED_PRODUCTS_BY_FRANCHISE_ID =
           """
           SELECT p.name, pb.stock, b.name AS branch_name
           FROM product_branch pb
           JOIN product p ON pb.product_id = p.id
           JOIN branch b ON pb.branch_id = b.id
           WHERE pb.stock = (
               SELECT MAX(stock) FROM product_branch WHERE branch_id = b.id
           )
           AND b.franchise_id = :franchiseId
           """;
}
