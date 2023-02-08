package com.example.sale.dao;

import com.example.sale.dao.entity.MenuEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public interface MenuRepository extends R2dbcRepository<MenuEntity, Long> {

}
