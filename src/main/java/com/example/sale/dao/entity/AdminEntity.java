package com.example.sale.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author xiaowenrou
 * @date 2023/2/27
 */
@Data
@Accessors(chain = true)
@Table(value = "sale_admin")
public class AdminEntity {

    @Id
    private Long id;

    private String userName;

    private String password;

}
