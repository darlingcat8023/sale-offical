package com.example.sale.model;

import com.example.sale.dao.entity.MailEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

/**
 * @author xiaowenrou
 * @date 2023/2/7
 */
public record MailSendRequest(

        @NotBlank
        String address,

        @NotBlank
        String sender,

        @NotBlank
        String content,

        @NotBlank
        String location

) {

    public MailEntity convert() {
        var entity = new MailEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }

}
