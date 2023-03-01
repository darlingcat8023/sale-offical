package com.example.sale.model;

import com.example.sale.dao.entity.CarouselImageEntity;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public record CarouselImageSaveRequest(

        Long id,

        String name,

        Long category,

        String title,

        String description,

        String image

) {

    public CarouselImageEntity convert() {
        var entity = new CarouselImageEntity();
        BeanUtils.copyProperties(this, entity);
        entity.setCreatedDate(LocalDateTime.now());
        return entity;
    }

}
