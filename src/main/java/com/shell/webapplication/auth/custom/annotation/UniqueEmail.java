package com.shell.webapplication.auth.custom.annotation;

import com.shell.webapplication.auth.custom.annotationimpl.UniqueEmailImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UniqueEmailImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    public String message() default "Invalid email";

    public Class<? extends Payload>[] payload() default {};

    public Class<?>[] groups() default {};

}
