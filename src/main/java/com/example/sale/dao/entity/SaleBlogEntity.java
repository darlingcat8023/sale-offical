package com.example.sale.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author xiaowenrou
 * @date 2023/2/7
 */
@Data
@Accessors(chain = true)
@Table(value = "sale_blog")
public class SaleBlogEntity {

    @Id
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    private String title;

    private String secondaryTitle;

    private String digest;

    private String image;

    private String content;

}
