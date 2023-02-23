package com.example.sale.handler;

import com.example.sale.dao.CarouselImageRepository;
import com.example.sale.dao.entity.CarouselImageEntity;
import com.example.sale.model.CarouselImageSaveRequest;
import com.example.sale.utils.ValidatorUtils;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
@Component
@AllArgsConstructor
public class CarouselImageHandler {

    private final CarouselImageRepository imageRepository;

    private final Validator validator;

    public Mono<ServerResponse> saveCarouselImage(ServerRequest request) {
        var mono = request.bodyToMono(CarouselImageSaveRequest.class).doOnNext(req -> ValidatorUtils.valid(this.validator, req))
                .map(CarouselImageSaveRequest::convert).flatMap(this.imageRepository::save).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> deleteCarouselImage(ServerRequest request) {
        var id = request.queryParam("id").map(Long::valueOf).orElseThrow(() -> new RuntimeException("param error"));
        var mono = this.imageRepository.deleteById(id).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> pageCarouselImage(ServerRequest request) {
        var pageable = PageRequest.of(request.queryParam("page").map(Integer::parseInt).orElse(0), 10);
        var sort = Sort.by("id").descending();
        var entity = new CarouselImageEntity();
        request.queryParam("name").ifPresent(entity::setName);
        request.queryParam("category").filter(StringUtils::hasText).map(Long::valueOf).ifPresent(entity::setCategory);
        var matcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains()).withIgnoreNullValues();
        var ret = this.imageRepository.findBy(Example.of(entity, matcher), fluent -> fluent.sortBy(sort).page(pageable));
        var type = new ParameterizedTypeReference<Page<CarouselImageEntity>>() {};
        return ServerResponse.ok().body(ret, type);
    }

    public Mono<ServerResponse> listCarouselImage(ServerRequest request) {
        var sort = Sort.by("id").descending();
        var entity = new CarouselImageEntity();
        request.queryParam("category").map(Long::valueOf).ifPresent(entity::setCategory);
        var matcher = ExampleMatcher.matching().withIgnoreNullValues();
        return ServerResponse.ok().body(this.imageRepository.findAll(Example.of(entity, matcher), sort), CarouselImageEntity.class);
    }

}
