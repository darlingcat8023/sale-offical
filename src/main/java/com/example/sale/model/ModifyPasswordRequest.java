package com.example.sale.model;

/**
 * @author xiaowenrou
 * @date 2023/2/27
 */
public record ModifyPasswordRequest(

        String userName,

        String password,

        String newPassword

) {
}
