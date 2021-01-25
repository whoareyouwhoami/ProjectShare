package com.project.share.validate;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<ValidCheckPasswordConfirm, Object> {
    private String field;
    private String fieldMatch;

    @Override
    public void initialize(ValidCheckPasswordConfirm constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String fieldPassword = (String) new BeanWrapperImpl(value).getPropertyValue(field);
        String fieldPasswordConfirm = (String) new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode("passwordConfirm").addConstraintViolation();
        return fieldPassword.equals(fieldPasswordConfirm);
    }
}
