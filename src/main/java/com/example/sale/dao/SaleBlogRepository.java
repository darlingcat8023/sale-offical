package com.example.sale.dao;

import com.example.sale.dao.entity.SaleBlogEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * @author xiaowenrou
 * @date 2023/2/7
 */
public interface SaleBlogRepository extends R2dbcRepository<SaleBlogEntity, Long> {

}
