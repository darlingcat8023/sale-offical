package com.example.sale.dao.entity;

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
@Table(value = "sale_carousel")
public class CarouselEntity {

    @Id
    private Long id;

    private String category;

}
