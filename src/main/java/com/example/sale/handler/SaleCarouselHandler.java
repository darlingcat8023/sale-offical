package com.example.sale.handler;

import com.example.sale.dao.CarouselRepository;
import com.example.sale.dao.entity.CarouselEntity;
import com.example.sale.model.CarouselSaveRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
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

    public Mono<ServerResponse> listCarousel(ServerRequest request) {
        var sort = Sort.by("id").descending();
        return ServerResponse.ok().body(this.carouselRepository.findAll(sort), CarouselEntity.class);
    }

}
