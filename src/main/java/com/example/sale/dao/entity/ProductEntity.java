package com.example.sale.dao.entity;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.experimental.Accessors;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.relational.core.mapping.Table;

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

    private String name;

    private String category;

    private String desc;

    private String image;

    private String specImage;

    @JsonRawValue
    private String properties;

    @JsonRawValue
    private String relationProduct;

    private String video;

    private String loadImage;

    private String detail;

    private String secondaryTitle;

}
