package com.shell.webapplication.auth.custom.annotation;

import com.shell.webapplication.auth.custom.annotationimpl.UniqueUserNameImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UniqueUserNameImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserName {

    public String message() default "Invalid Username";

    public Class<? extends Payload>[] payload() default {};

    public Class<?>[] groups() default {};

}
