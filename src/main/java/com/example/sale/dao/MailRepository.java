package com.example.sale.dao;

import com.example.sale.dao.entity.MailEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * @author xiaowenrou
 * @date 2023/2/7
 */
public interface MailRepository extends R2dbcRepository<MailEntity, Long> {

}
