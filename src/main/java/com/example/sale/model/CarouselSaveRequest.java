package com.example.sale.model;

import com.example.sale.dao.entity.CarouselEntity;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public record CarouselSaveRequest(

        Long id,

        String category

) {

    public CarouselEntity convert() {
        return new CarouselEntity().setId(this.id).setCategory(this.category);
    }

}
