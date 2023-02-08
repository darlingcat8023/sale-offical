package com.example.sale.handler;

import com.example.sale.dao.CarouselRepository;
import com.example.sale.dao.entity.CarouselEntity;
import com.example.sale.model.CarouselSaveRequest;
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
public class SaleCarouselHandler {

    private final CarouselRepository carouselRepository;

    public Mono<ServerResponse> saveCarousel(ServerRequest request) {
        var mono = request.bodyToMono(CarouselSaveRequest.class).map(CarouselSaveRequest::convert)
                .flatMap(this.carouselRepository::save).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> pageCarousel(ServerRequest request) {
        var pageable = PageRequest.of(request.queryParam("page").map(Integer::parseInt).orElse(0), 10);
        var sort = Sort.by("id").ascending();
        var entity = new CarouselEntity();
        var ret = this.carouselRepository.findBy(Example.of(entity, ExampleMatcher.matching().withIgnoreNullValues()), fluent -> fluent.sortBy(sort).page(pageable));
        var type = new ParameterizedTypeReference<Page<CarouselEntity>>() {};
        return ServerResponse.ok().body(ret, CarouselEntity.class);
    }

}
