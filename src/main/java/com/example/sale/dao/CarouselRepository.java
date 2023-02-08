package com.example.sale.dao;

import com.example.sale.dao.entity.CarouselEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public interface CarouselRepository extends R2dbcRepository<CarouselEntity, Long> {
}
