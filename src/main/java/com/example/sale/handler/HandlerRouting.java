package com.example.sale.handler;

import com.example.sale.dao.entity.BlogEntity;
import com.example.sale.dao.entity.MenuEntity;
import com.example.sale.dao.entity.ProductEntity;
import com.example.sale.model.*;
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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;
import java.util.function.Supplier;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author xiaowenrou
 * @date 2023/2/6
 */
@Configuration(proxyBeanMethods = false)
public class HandlerRouting {

    @Bean
    public RouterFunction<ServerResponse> fileRouterFunction(FileHandler handler) {
        Supplier<RouterFunction<ServerResponse>> supplier = () -> RouterFunctions.route()
                .POST("/upload", RequestPredicates.contentType(MULTIPART_FORM_DATA), handler::fileUpload)
                .build();
        return RouterFunctions.route().path("/api/file", supplier).build();
    }

    @Bean
    @RouterOperations(value = {
            @RouterOperation(method = POST, path = "/api/blog/save", produces = MediaType.APPLICATION_JSON_VALUE, beanClass = SaleBlogHandler.class, beanMethod = "saveBlog",
                    operation = @Operation(
                            operationId = "saveBlog",
                            description = "??????blog",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = BlogSaveRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/blog/delete", beanClass = SaleBlogHandler.class, beanMethod = "deleteBlog",
                    operation = @Operation(
                            operationId = "deleteBlog",
                            description = "??????blog",
                            parameters = {
                                    @Parameter(in = QUERY, name = "id", description = "????????????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/blog/page", beanClass = SaleBlogHandler.class, beanMethod = "pageBlog",
                    operation = @Operation(
                            operationId = "pageBlog",
                            description = "????????????blog",
                            parameters = {
                                    @Parameter(in = QUERY, name = "page", description = "??????????????????????????????10???"),
                                    @Parameter(in = QUERY, name = "title", description = "??????????????????"),
                                    @Parameter(in = QUERY, name = "startTime", description = "??????????????????"),
                                    @Parameter(in = QUERY, name = "endTime", description = "??????????????????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Page.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/blog/list", beanClass = SaleBlogHandler.class, beanMethod = "listBlog",
                    operation = @Operation(
                            operationId = "listBlog",
                            description = "??????blog??????",
                            parameters = {
                                    @Parameter(in = QUERY, name = "title", description = "??????????????????"),
                                    @Parameter(in = QUERY, name = "startTime", description = "??????????????????"),
                                    @Parameter(in = QUERY, name = "endTime", description = "??????????????????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = List.class))),
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/blog/detail", beanClass = SaleBlogHandler.class, beanMethod = "detailBlog",
                    operation = @Operation(
                            operationId = "detailBlog",
                            description = "blog??????",
                            parameters = {
                                    @Parameter(in = QUERY, name = "id", description = "id")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = BlogEntity.class))),
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> saleBlogRouterFunction(SaleBlogHandler handler) {
        Supplier<RouterFunction<ServerResponse>> supplier = () -> RouterFunctions.route()
                .POST("/save", RequestPredicates.contentType(APPLICATION_JSON), handler::saveBlog)
                .GET("/delete", handler::deleteBlog)
                .GET("/page", handler::pageBlog)
                .GET("/list", handler::listBlog)
                .GET("/detail", handler::detailBlog)
                .build();
        return RouterFunctions.route().path("/api/blog", supplier).build();
    }

    @Bean
    @RouterOperations(value = {
            @RouterOperation(method = POST, path = "/api/mail/send", produces = MediaType.APPLICATION_JSON_VALUE, beanClass = SaleMailHandler.class, beanMethod = "sendMail",
                    operation = @Operation(
                            operationId = "sendMaid",
                            description = "????????????",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = MailSendRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/mail/page", beanClass = SaleMailHandler.class, beanMethod = "pageMail",
                    operation = @Operation(
                            operationId = "pageMail",
                            description = "??????????????????",
                            parameters = {
                                    @Parameter(in = QUERY, name = "page", description = "??????????????????????????????10???"),
                                    @Parameter(in = QUERY, name = "address", description = "????????????????????????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Page.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> mailRouterFunction(SaleMailHandler handler) {
        Supplier<RouterFunction<ServerResponse>> supplier = () -> RouterFunctions.route()
                .POST("/send", RequestPredicates.contentType(APPLICATION_JSON), handler::sendMail)
                .GET("/page", handler::pageMail)
                .build();
        return RouterFunctions.route().path("/api/mail", supplier).build();
    }

    @Bean
    @RouterOperations(value = {
            @RouterOperation(method = POST, path = "/api/seller/save", produces = MediaType.APPLICATION_JSON_VALUE, beanClass = SaleSellerHandler.class, beanMethod = "saveSeller",
                    operation = @Operation(
                            operationId = "saveSeller",
                            description = "??????????????????",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = SellerSaveRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/seller/delete", beanClass = SaleSellerHandler.class, beanMethod = "deleteSeller",
                    operation = @Operation(
                            operationId = "deleteSeller",
                            description = "??????????????????",
                            parameters = {
                                    @Parameter(in = QUERY, name = "id", description = "????????????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/seller/page", beanClass = SaleSellerHandler.class, beanMethod = "pageSeller",
                    operation = @Operation(
                            operationId = "pageSeller",
                            description = "????????????Seller",
                            parameters = {
                                    @Parameter(in = QUERY, name = "page", description = "??????????????????????????????10???")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Page.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/seller/list", beanClass = SaleSellerHandler.class, beanMethod = "listSeller",
                    operation = @Operation(
                            operationId = "listSeller",
                            description = "????????????????????????",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = List.class))),
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> sellerRouterFunction(SaleSellerHandler handler) {
        Supplier<RouterFunction<ServerResponse>> supplier = () -> RouterFunctions.route()
                .POST("/save", RequestPredicates.contentType(APPLICATION_JSON), handler::saveSeller)
                .GET("/delete", handler::deleteSeller)
                .GET("/page", handler::pageSeller)
                .GET("/list", handler::listSeller)
                .build();
        return RouterFunctions.route().path("/api/seller", supplier).build();
    }

    @Bean
    @RouterOperations(value = {
            @RouterOperation(method = POST, path = "/api/product/save", produces = MediaType.APPLICATION_JSON_VALUE, beanClass = SaleProductHandler.class, beanMethod = "saveProduct",
                    operation = @Operation(
                            operationId = "saveProduct",
                            description = "????????????",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = ProductSaveRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/product/delete", beanClass = SaleProductHandler.class, beanMethod = "deleteProduct",
                    operation = @Operation(
                            operationId = "deleteProduct",
                            description = "????????????",
                            parameters = {
                                    @Parameter(in = QUERY, name = "id", description = "????????????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/product/detail", beanClass = SaleProductHandler.class, beanMethod = "detailProduct",
                    operation = @Operation(
                            operationId = "detailProduct",
                            description = "????????????",
                            parameters = {
                                    @Parameter(in = QUERY, name = "id", description = "??????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ProductEntity.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/product/page", beanClass = SaleProductHandler.class, beanMethod = "pageProduct",
                    operation = @Operation(
                            operationId = "pageProduct",
                            description = "????????????Product",
                            parameters = {
                                    @Parameter(in = QUERY, name = "page", description = "??????????????????????????????10???"),
                                    @Parameter(in = QUERY, name = "name", description = "?????????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Page.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/product/list", beanClass = SaleProductHandler.class, beanMethod = "listProduct",
                    operation = @Operation(
                            operationId = "listProduct",
                            description = "??????Product??????",
                            parameters = {
                                    @Parameter(in = QUERY, name = "category", description = "????????????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = List.class))),
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> productRouterFunction(SaleProductHandler handler) {
        Supplier<RouterFunction<ServerResponse>> supplier = () -> RouterFunctions.route()
                .POST("/save", RequestPredicates.contentType(APPLICATION_JSON), handler::saveProduct)
                .GET("/delete", handler::deleteProduct)
                .GET("/detail", handler::detailProduct)
                .GET("/page", handler::pageProduct)
                .GET("/list", handler::listProduct)
                .build();
        return RouterFunctions.route().path("/api/product", supplier).build();
    }

    @Bean
    @RouterOperations(value = {
            @RouterOperation(method = POST, path = "/api/menu/save", produces = MediaType.APPLICATION_JSON_VALUE, beanClass = SaleMenuHandler.class, beanMethod = "saveMenu",
                    operation = @Operation(
                            operationId = "saveMenu",
                            description = "????????????",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = MenuSaveRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/menu/get", beanClass = SaleMenuHandler.class, beanMethod = "getMenu",
                    operation = @Operation(
                            operationId = "getMenu",
                            description = "????????????",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = MenuEntity.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> menuRouterFunction(SaleMenuHandler handler) {
        Supplier<RouterFunction<ServerResponse>> supplier = () -> RouterFunctions.route()
                .POST("/save", RequestPredicates.contentType(APPLICATION_JSON), handler::saveMenu)
                .GET("/get", handler::getMenu)
                .build();
        return RouterFunctions.route().path("/api/menu", supplier).build();
    }

    @Bean
    @RouterOperations(value = {
            @RouterOperation(method = POST, path = "/api/carousel/save", produces = MediaType.APPLICATION_JSON_VALUE, beanClass = SaleCarouselHandler.class, beanMethod = "saveCarousel",
                    operation = @Operation(
                            operationId = "saveCarousel",
                            description = "??????????????????",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = CarouselSaveRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/carousel/page", beanClass = SaleCarouselHandler.class, beanMethod = "pageCarousel",
                    operation = @Operation(
                            operationId = "pageCarousel",
                            description = "??????????????????",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = List.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> carouselRouterFunction(SaleCarouselHandler handler) {
        Supplier<RouterFunction<ServerResponse>> supplier = () -> RouterFunctions.route()
                .POST("/save", RequestPredicates.contentType(APPLICATION_JSON), handler::saveCarousel)
                .GET("/page", handler::pageCarousel)
                .build();
        return RouterFunctions.route().path("/api/carousel", supplier).build();
    }

    @Bean
    @RouterOperations(value = {
            @RouterOperation(method = POST, path = "/api/carouselImage/save", produces = MediaType.APPLICATION_JSON_VALUE, beanClass = CarouselImageHandler.class, beanMethod = "saveCarouselImage",
                    operation = @Operation(
                            operationId = "saveCarouselImage",
                            description = "??????CarouselImage",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = CarouselImageSaveRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/carouselImage/delete", beanClass = CarouselImageHandler.class, beanMethod = "deleteCarouselImage",
                    operation = @Operation(
                            operationId = "deleteCarouselImage",
                            description = "??????CarouselImage",
                            parameters = {
                                    @Parameter(in = QUERY, name = "id", description = "????????????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/carouselImage/page", beanClass = CarouselImageHandler.class, beanMethod = "pageCarouselImage",
                    operation = @Operation(
                            operationId = "pageCarouselImage",
                            description = "????????????CarouselImage",
                            parameters = {
                                    @Parameter(in = QUERY, name = "page", description = "??????????????????????????????10???"),
                                    @Parameter(in = QUERY, name = "name", description = "??????")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Page.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/carouselImage/list", beanClass = CarouselImageHandler.class, beanMethod = "listCarouselImage",
                    operation = @Operation(
                            operationId = "listCarouselImage",
                            description = "??????CarouselImage??????",
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = List.class))),
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> carouselImageRouterFunction(CarouselImageHandler handler) {
        Supplier<RouterFunction<ServerResponse>> supplier = () -> RouterFunctions.route()
                .POST("/save", RequestPredicates.contentType(APPLICATION_JSON), handler::saveCarouselImage)
                .GET("/delete", handler::deleteCarouselImage)
                .GET("/page", handler::pageCarouselImage)
                .GET("/list", handler::listCarouselImage)
                .build();
        return RouterFunctions.route().path("/api/carouselImage", supplier).build();
    }

    @Bean
    public RouterFunction<ServerResponse> adminRouterFunction(AdminHandler handler) {
        Supplier<RouterFunction<ServerResponse>> supplier = () -> RouterFunctions.route()
                .POST("/login", RequestPredicates.contentType(APPLICATION_JSON), handler::login)
                .POST("/modify", RequestPredicates.contentType(APPLICATION_JSON), handler::modifyPassword)
                .build();
        return RouterFunctions.route().path("/api/admin", supplier).build();
    }

}
