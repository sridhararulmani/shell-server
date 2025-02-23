package com.shell.webapplication.auth.custom.annotationimpl;

import com.shell.webapplication.auth.custom.annotation.UniqueUserName;
import com.shell.webapplication.auth.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class UniqueUserNameImpl implements ConstraintValidator<UniqueUserName, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        /** If Username is Exists isValid Should Return false, if not exists should return true **/
        if (Objects.isNull(value)) {
            return true;
        }
        return !userRepository.existsByUserName(value);
    }
}
