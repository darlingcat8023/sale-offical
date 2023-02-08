package com.example.sale.dao;

import com.example.sale.dao.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public interface ProductRepository extends R2dbcRepository<ProductEntity, Long> {

    /**
     * 多查询
     * @param cates
     * @return
     */
    Flux<ProductEntity> findByCategoryIn(Collection<String> cates);

}
