package com.example.sale.dao;

import com.example.sale.dao.entity.AdminEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * @author xiaowenrou
 * @date 2023/2/27
 */
public interface AdminRepository extends R2dbcRepository<AdminEntity, Long> {

    Mono<AdminEntity> findByUserNameAndPassword(String userName, String password);

}
