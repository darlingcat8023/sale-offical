package com.example.sale.handler;

import com.example.sale.dao.SellerRepository;
import com.example.sale.dao.entity.SellerEntity;
import com.example.sale.model.SellerSaveRequest;
import com.example.sale.utils.ValidatorUtils;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
@Component
@AllArgsConstructor
public class SaleSellerHandler {

    private final SellerRepository sellerRepository;

    private final Validator validator;

    public Mono<ServerResponse> saveSeller(ServerRequest request) {
        var mono = request.bodyToMono(SellerSaveRequest.class).doOnNext(req -> ValidatorUtils.valid(this.validator, req))
                .map(SellerSaveRequest::convert).flatMap(this.sellerRepository::save).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> deleteSeller(ServerRequest request) {
        var id = request.queryParam("id").orElseThrow(() -> new RuntimeException("param error"));
        var mono = this.sellerRepository.deleteById(Long.valueOf(id)).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> pageSeller(ServerRequest request) {
        var pageable = PageRequest.of(request.queryParam("page").map(Integer::parseInt).orElse(0), 10);
        var sort = Sort.by("id").ascending();
        var ret = this.sellerRepository.findBy(Example.of(new SellerEntity(), ExampleMatcher.matching().withIgnoreNullValues()), fluent -> fluent.sortBy(sort).page(pageable));
        var type = new ParameterizedTypeReference<Page<SellerEntity>>() {};
        return ServerResponse.ok().body(ret, type);
    }

    public Mono<ServerResponse> listSeller(ServerRequest request) {
        return ServerResponse.ok().body(this.sellerRepository.findAll(), SellerEntity.class);
    }

}
