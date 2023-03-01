package com.example.sale.model;

import com.example.sale.dao.entity.BlogEntity;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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

    public BlogEntity convert() {
        var entity = new BlogEntity();
        BeanUtils.copyProperties(this, entity);
        entity.setCreatedDate(LocalDateTime.now());
        return entity;
    }

}
