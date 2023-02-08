package com.example.sale.dao;

import com.example.sale.dao.entity.BlogEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * @author xiaowenrou
 * @date 2023/2/7
 */
public interface BlogRepository extends R2dbcRepository<BlogEntity, Long> {

}
