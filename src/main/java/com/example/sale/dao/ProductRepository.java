package com.example.sale.dao;

import com.example.sale.dao.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public interface ProductRepository extends R2dbcRepository<ProductEntity, Long> {

}
