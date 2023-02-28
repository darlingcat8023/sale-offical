package com.example.sale.utils;


import javax.validation.Validator;

/**
 * @author xiaowenrou
 * @data 2022/7/28
 */
public abstract class ValidatorUtils {

    /**
     * 参数校验
     * @param validator
     * @param object
     * @param groups
     */
    public static void valid(Validator validator, Object object, Class<?> ... groups) {
        if (validator == null) {
            throw new RuntimeException("参数检查器获取失败");
        }
        var res = validator.validate(object, groups);
        res.stream().findFirst().ifPresent(constraintViolation -> {
            throw new RuntimeException(constraintViolation.getMessage());
        });
    }

}
