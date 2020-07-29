package com.zaogao.miaosha.validator;

import com.mysql.cj.util.StringUtils;
import com.zaogao.miaosha.util.ValidatorUtil;

import javax.swing.*;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private  boolean required = false;

    //初始化方法
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required){
            return ValidatorUtil.isMobile(value);

        }else {
            if (StringUtils.isEmptyOrWhitespaceOnly(value)){
                return  true;
            }else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
