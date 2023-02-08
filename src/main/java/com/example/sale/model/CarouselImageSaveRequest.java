package com.example.sale.model;

import com.example.sale.dao.entity.CarouselImageEntity;
import org.springframework.beans.BeanUtils;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public record CarouselImageSaveRequest(

        Long id,

        String name,

        Long category,

        String title,

        String desc,

        String image

) {

    public CarouselImageEntity convert() {
        var entity = new CarouselImageEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }

}
