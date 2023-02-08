package com.example.sale.handler;

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
                            description = "保存blog",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = BlogSaveRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/blog/delete", beanClass = SaleBlogHandler.class, beanMethod = "deleteBlog",
                    operation = @Operation(
                            operationId = "deleteBlog",
                            description = "删除blog",
                            parameters = {
                                    @Parameter(in = QUERY, name = "id", description = "主键删除")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/blog/page", beanClass = SaleBlogHandler.class, beanMethod = "pageBlog",
                    operation = @Operation(
                            operationId = "pageBlog",
                            description = "后台分页blog",
                            parameters = {
                                    @Parameter(in = QUERY, name = "page", description = "分页查询的页数，每页10条"),
                                    @Parameter(in = QUERY, name = "title", description = "标题模糊查询"),
                                    @Parameter(in = QUERY, name = "startTime", description = "创建时间开始"),
                                    @Parameter(in = QUERY, name = "endTime", description = "创建时间结束")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Page.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/blog/list", beanClass = SaleBlogHandler.class, beanMethod = "listBlog",
                    operation = @Operation(
                            operationId = "listBlog",
                            description = "前台blog列表",
                            parameters = {
                                    @Parameter(in = QUERY, name = "title", description = "标题模糊查询"),
                                    @Parameter(in = QUERY, name = "startTime", description = "创建时间开始"),
                                    @Parameter(in = QUERY, name = "endTime", description = "创建时间结束")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = List.class))),
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
                .build();
        return RouterFunctions.route().path("/api/blog", supplier).build();
    }

    @Bean
    @RouterOperations(value = {
            @RouterOperation(method = POST, path = "/api/mail/send", produces = MediaType.APPLICATION_JSON_VALUE, beanClass = SaleMailHandler.class, beanMethod = "sendMail",
                    operation = @Operation(
                            operationId = "sendMaid",
                            description = "发送邮件",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = MailSendRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/mail/page", beanClass = SaleMailHandler.class, beanMethod = "pageMail",
                    operation = @Operation(
                            operationId = "pageMail",
                            description = "后台分页邮件",
                            parameters = {
                                    @Parameter(in = QUERY, name = "page", description = "分页查询的页数，每页10条"),
                                    @Parameter(in = QUERY, name = "address", description = "邮件地址模糊查询")
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
                            description = "保存销售经理",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = SellerSaveRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/seller/delete", beanClass = SaleSellerHandler.class, beanMethod = "deleteSeller",
                    operation = @Operation(
                            operationId = "deleteSeller",
                            description = "删除销售经理",
                            parameters = {
                                    @Parameter(in = QUERY, name = "id", description = "主键删除")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/seller/page", beanClass = SaleSellerHandler.class, beanMethod = "pageSeller",
                    operation = @Operation(
                            operationId = "pageSeller",
                            description = "后台分页Seller",
                            parameters = {
                                    @Parameter(in = QUERY, name = "page", description = "分页查询的页数，每页10条")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Page.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/seller/list", beanClass = SaleSellerHandler.class, beanMethod = "listSeller",
                    operation = @Operation(
                            operationId = "listSeller",
                            description = "前台销售经理列表",
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
                            description = "保存产品",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = ProductSaveRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/product/delete", beanClass = SaleProductHandler.class, beanMethod = "deleteProduct",
                    operation = @Operation(
                            operationId = "deleteProduct",
                            description = "删除产品",
                            parameters = {
                                    @Parameter(in = QUERY, name = "id", description = "主键删除")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/product/detail", beanClass = SaleProductHandler.class, beanMethod = "detailProduct",
                    operation = @Operation(
                            operationId = "detailProduct",
                            description = "产品详情",
                            parameters = {
                                    @Parameter(in = QUERY, name = "id", description = "主键")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ProductEntity.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/product/page", beanClass = SaleSellerHandler.class, beanMethod = "pageProduct",
                    operation = @Operation(
                            operationId = "pageProduct",
                            description = "后台分页Product",
                            parameters = {
                                    @Parameter(in = QUERY, name = "page", description = "分页查询的页数，每页10条"),
                                    @Parameter(in = QUERY, name = "name", description = "产品名")
                            },
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Page.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/product/list", beanClass = SaleSellerHandler.class, beanMethod = "listProduct",
                    operation = @Operation(
                            operationId = "listProduct",
                            description = "前台Product列表",
                            parameters = {
                                    @Parameter(in = QUERY, name = "category", description = "产品类型")
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
                            description = "保存菜单",
                            requestBody = @RequestBody(required = true, content = @Content(schema = @Schema(implementation = MenuSaveRequest.class))),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = String.class)))
                            }
                    )
            ),
            @RouterOperation(method = GET, path = "/api/menu/get", beanClass = SaleMenuHandler.class, beanMethod = "getMenu",
                    operation = @Operation(
                            operationId = "getMenu",
                            description = "获取菜单",
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

}
