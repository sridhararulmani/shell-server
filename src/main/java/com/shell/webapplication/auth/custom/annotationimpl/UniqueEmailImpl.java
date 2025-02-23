package com.shell.webapplication.auth.custom.annotationimpl;

import com.shell.webapplication.auth.custom.annotation.UniqueEmail;
import com.shell.webapplication.auth.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class UniqueEmailImpl implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        /** If Email is Exists isValid Should Return false, if not exists should return true **/
        if (Objects.isNull(value)) {
            return true;
        }
        return !userRepository.existsByUserEmail(value);
    }
}
