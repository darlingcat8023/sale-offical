package com.example.sale.model;

import com.example.sale.dao.entity.MenuEntity;

/**
 * @author xiaowenrou
 * @date 2023/2/8
 */
public record MenuSaveRequest(

        Long id,

        String text

) {

    public MenuEntity convert() {
        return new MenuEntity().setId(this.id).setText(this.text);
    }

}
