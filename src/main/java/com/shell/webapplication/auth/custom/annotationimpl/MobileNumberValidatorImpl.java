package com.shell.webapplication.auth.custom.annotationimpl;

import com.shell.webapplication.auth.custom.annotation.MobileNumberValidator;
import com.shell.webapplication.constent.AppConstant;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class MobileNumberValidatorImpl implements ConstraintValidator<MobileNumberValidator, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }
        System.out.println(value);
        return value.matches(AppConstant.MOBILE_NUMBER_REGEX);
    }

}
