package com.shell.webapplication.auth.custom.annotation;

import com.shell.webapplication.auth.custom.annotationimpl.MobileNumberValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = MobileNumberValidatorImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MobileNumberValidator {

    public String message() default "Invalid Mobile number";

    public Class<? extends Payload>[] payload() default {};

    public Class<?>[] groups() default {};

}
