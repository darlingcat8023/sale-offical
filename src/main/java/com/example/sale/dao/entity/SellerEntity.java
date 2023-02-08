package com.example.sale.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
@Data
@Accessors(chain = true)
@Table(value = "sale_seller")
public class SellerEntity {

    @Id
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    private String name;

    private String phone;

    private String mail;

    private String fox;

    private String position;

    private String image;

}
