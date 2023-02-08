package com.example.sale.handler;

import com.example.sale.dao.MenuRepository;
import com.example.sale.dao.entity.MenuEntity;
import com.example.sale.model.MenuSaveRequest;
import lombok.AllArgsConstructor;
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
public class SaleMenuHandler {

    private final MenuRepository menuRepository;

    public Mono<ServerResponse> saveMenu(ServerRequest request) {
        var mono = request.bodyToMono(MenuSaveRequest.class).map(MenuSaveRequest::convert)
                .flatMap(this.menuRepository::save).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> getMenu(ServerRequest request) {
        return ServerResponse.ok().body(this.menuRepository.findAll().elementAt(0), MenuEntity.class);
    }

}
