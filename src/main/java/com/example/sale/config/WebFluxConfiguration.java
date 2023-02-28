package com.example.sale.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.lang.NonNull;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author xiaowenrou
 * @date 2023/2/6
 */
@EnableWebFlux
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class WebFluxConfiguration implements WebFluxConfigurer {

    private final ObjectMapper objectMapper;

    private final javax.validation.Validator validator;

    private final Environment environment;

    /**
     * Webflux跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        WebFluxConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**").allowedOriginPatterns("*").allowedHeaders("*")
                .allowedMethods("GET", "POST", "OPTIONS", "DELETE", "PUT");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebFluxConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/**").addResourceLocations("file:" + environment.getProperty("filePath"));
    }

    /**
     * Webflux参数校验，在function模式下仅用来注入一个Adapter
     * @return
     */
    @Override
    public Validator getValidator() {
        return new SpringValidatorAdapter(this.validator);
    }

    /**
     * 序列化和反序列化
     * @param configurer
     */
    @Override
    public void configureHttpMessageCodecs(@NonNull ServerCodecConfigurer configurer) {
        WebFluxConfigurer.super.configureHttpMessageCodecs(configurer);
        // 配置jackson
        var serverDefaultCodecs = configurer.defaultCodecs();
        // 启用debug日志记录http请求
        serverDefaultCodecs.enableLoggingRequestDetails(true);
        // 设置默认编码解码器
        serverDefaultCodecs.jackson2JsonEncoder(new Jackson2JsonEncoder(this.objectMapper));
        serverDefaultCodecs.jackson2JsonDecoder(new Jackson2JsonDecoder(this.objectMapper));
    }

}
