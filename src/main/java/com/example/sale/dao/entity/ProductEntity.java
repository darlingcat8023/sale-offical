package com.example.sale.dao.entity;

import com.fasterxml.jackson.annotation.JsonRawValue;
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
@Table(value = "sale_product")
public class ProductEntity {

    @Id
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    private String name;

    private String category;

    private String description;

    private String image;

    private String specImage;

    @JsonRawValue
    private String properties;

    @JsonRawValue
    private String relationProduct;

    private String video;

    @JsonRawValue
    private String loadImage;

    private String detail;

    private String secondaryTitle;

}
