package com.example.sale.dao;

import com.example.sale.dao.entity.SellerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public interface SellerRepository extends R2dbcRepository<SellerEntity, Long> {
}
