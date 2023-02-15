package com.example.sale.dao.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
@Data
@Accessors(chain = true)
@Table(value = "sale_carousel_image")
public class CarouselImageEntity {

    @Id
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    private String name;

    private Long category;

    private String title;

    private String description;

    private String image;

}
