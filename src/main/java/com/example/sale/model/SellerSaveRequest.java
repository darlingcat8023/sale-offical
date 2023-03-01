package com.example.sale.model;

import com.example.sale.dao.entity.SellerEntity;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
        entity.setCreatedDate(LocalDateTime.now());
        return entity;
    }

}
