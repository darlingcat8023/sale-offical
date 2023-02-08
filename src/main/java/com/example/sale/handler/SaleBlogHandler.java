package com.example.sale.handler;

import com.example.sale.dao.BlogRepository;
import com.example.sale.dao.entity.BlogEntity;
import com.example.sale.model.BlogSaveRequest;
import com.example.sale.utils.ReactivePageUtils;
import com.example.sale.utils.ValidatorUtils;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xiaowenrou
 * @date 2023/2/7
 */
@Component
@AllArgsConstructor
public class SaleBlogHandler {

    private final BlogRepository saleBlogRepository;

    private final R2dbcEntityOperations entityOperations;

    private final Validator validator;

    public Mono<ServerResponse> saveBlog(ServerRequest request) {
        var mono = request.bodyToMono(BlogSaveRequest.class).doOnNext(req -> ValidatorUtils.valid(this.validator, req))
                .map(BlogSaveRequest::convert).flatMap(this.saleBlogRepository::save).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> deleteBlog(ServerRequest request) {
        var id = request.queryParam("id").map(Long::valueOf).orElseThrow(() -> new RuntimeException("param error"));
        var mono = this.saleBlogRepository.deleteById(id).thenReturn("success");
        return ServerResponse.ok().body(mono, String.class);
    }

    public Mono<ServerResponse> pageBlog(ServerRequest request) {
        var pageable = PageRequest.of(request.queryParam("page").map(Integer::parseInt).orElse(0), 10);
        var sort = Sort.by("id").descending();
        var matching = this.entityOperations.select(BlogEntity.class).matching(this.buildQuery(request).with(pageable).sort(sort));
        var type = new ParameterizedTypeReference<Page<BlogEntity>>() {};
        return ServerResponse.ok().body(matching.all().collectList().flatMap(content -> ReactivePageUtils.getPage(content, pageable, matching.count())), type);
    }

    public Mono<ServerResponse> listBlog(ServerRequest request) {
        var sort = Sort.by("id").descending();
        var matching = this.entityOperations.select(BlogEntity.class).matching(this.buildQuery(request).sort(sort)).all();
        return ServerResponse.ok().body(matching, BlogEntity.class);
    }

    private Query buildQuery(ServerRequest request) {
        final var criteria = new AtomicReference<>(Criteria.empty());
        request.queryParam("title").filter(StringUtils::hasText).ifPresent(param -> {
            var ct = criteria.get();
            criteria.set(ct.and(Criteria.where("title").like("%" + param + "%")));
        });
        request.queryParam("startTime").filter(StringUtils::hasText).ifPresent(param -> {
            var ct = criteria.get();
            criteria.set(ct.and(Criteria.where("created_date").greaterThanOrEquals(param)));
        });
        request.queryParam("endTime").filter(StringUtils::hasText).ifPresent(param -> {
            var ct = criteria.get();
            criteria.set(ct.and(Criteria.where("created_date").lessThanOrEquals(param)));
        });
        return Query.query(criteria.get());
    }

}
