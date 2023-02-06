package com.example.sale.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.function.Supplier;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

/**
 * @author xiaowenrou
 * @date 2023/2/6
 */
@Configuration(proxyBeanMethods = false)
public class HandlerRouting {

    @Bean
    public RouterFunction<ServerResponse> fileRouterFunction(FileHandler fileHandler) {
        Supplier<RouterFunction<ServerResponse>> supplier = () -> RouterFunctions.route()
                .POST("/upload", RequestPredicates.contentType(MULTIPART_FORM_DATA), fileHandler::fileUpload)
                .build();
        return RouterFunctions.route().path("/api/file", supplier).build();
    }

}
