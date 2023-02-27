package com.example.sale.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

/**
 * Webflux全局异常处理
 * @author xiaowenrou
 * @date 2022/8/11
 */
@Slf4j
@Order(value = -2)
@Configuration(proxyBeanMethods = false)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    private final ApplicationContext applicationContext;

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties properties, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, properties.getResources(), applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        HandlerFunction<ServerResponse> handlerFunction = request -> {
            final var error = errorAttributes.getError(request);
            return ServerResponse.badRequest().body(Mono.just(error.getMessage()), String.class);
        };
        return RouterFunctions.route(RequestPredicates.all(), handlerFunction);
    }

}