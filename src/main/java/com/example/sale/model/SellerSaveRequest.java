package com.example.sale.model;

import com.example.sale.dao.entity.SellerEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public record SellerSaveRequest(
        Long id,

        @NotBlank
        String name,

        String phone,

        String mail,

        String fox,

        String position,


        @NotBlank
        String image

) {

    public SellerEntity convert() {
        var entity = new SellerEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }

}
