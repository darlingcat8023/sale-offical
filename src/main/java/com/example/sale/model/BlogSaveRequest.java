package com.example.sale.model;

import com.example.sale.dao.entity.SaleBlogEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

/**
 * @author xiaowenrou
 * @date 2023/2/7
 */
public record BlogSaveRequest(
        Long id,

        @NotBlank
        String title,

        @NotBlank
        String secondaryTitle,

        String digest,

        @NotBlank
        String image,

        @NotBlank
        String content
) {

    public SaleBlogEntity convert() {
        var entity = new SaleBlogEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }

}
