package com.example.sale.model;

import com.example.sale.dao.entity.SellerEntity;
import org.springframework.beans.BeanUtils;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public record SellerSaveRequest(
        Long id,

        String name,

        String phone,

        String mail,

        String fox,

        String image

) {

    public SellerEntity convert() {
        var entity = new SellerEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }

}
