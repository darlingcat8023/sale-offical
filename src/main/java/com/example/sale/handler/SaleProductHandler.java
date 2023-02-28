package com.example.sale.handler;

import com.example.sale.dao.ProductRepository;
import com.example.sale.dao.entity.ProductEntity;
import com.example.sale.model.ProductSaveRequest;
import com.example.sale.utils.ValidatorUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Validator;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
@Component
@AllArgsConstructor
public class SaleProductHandler {

    private final ProductRepository productRepository;

    private final Validator validator;

    private final ObjectMapper mapper;

    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        var mono = request.bodyToMono(ProductSaveRequest.class).doOnNext(req -> ValidatorUtils.valid(this.validator, req))
                .map(req -> req.convert(this.mapper)).flatMap(this.productRepository::save).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        var id = request.queryParam("id").map(Long::valueOf).orElseThrow(() -> new RuntimeException("param error"));
        var mono = this.productRepository.deleteById(id).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> detailProduct(ServerRequest request) {
        var id = request.queryParam("id").map(Long::valueOf).orElseThrow(() -> new RuntimeException("param error"));
        return ServerResponse.ok().body(this.productRepository.findById(id), ProductEntity.class);
    }

    public Mono<ServerResponse> pageProduct(ServerRequest request) {
        var pageable = PageRequest.of(request.queryParam("page").map(Integer::parseInt).orElse(0), 10);
        var sort = Sort.by("id").descending();
        var entity = new ProductEntity();
        request.queryParam("name").ifPresent(entity::setName);
        var matcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains()).withIgnoreNullValues();
        var ret = this.productRepository.findBy(Example.of(entity, matcher), fluent -> fluent.sortBy(sort).page(pageable));
        var type = new ParameterizedTypeReference<Page<ProductEntity>>() {};
        return ServerResponse.ok().body(ret, type);
    }

    public Mono<ServerResponse> listProduct(ServerRequest request) {
        var entity = new ProductEntity();
        request.queryParam("category").filter(StringUtils::hasText).ifPresent(entity::setCategory);
        return ServerResponse.ok().body(this.productRepository.findAll(Example.of(entity)), ProductEntity.class);
    }

}
