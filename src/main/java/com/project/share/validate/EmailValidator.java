package com.project.share.validate;

import com.project.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidCheckEmail, String> {
    @Autowired
    private UserService userService;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Override
    public void initialize(ValidCheckEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.matches())
            return false;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Email address exist").addConstraintViolation();

        try {
            return !userService.userExist(value);
        } catch (NullPointerException e) {
            return true;
        }
    }
}

