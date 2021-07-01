package com.project.share.validate;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ProjectDateDifferenceValidator implements ConstraintValidator<ValidateProjectDateDifference, Object> {
    private String dateStart;
    private String dateEnd;

    @Override
    public void initialize(ValidateProjectDateDifference constraintAnnotation) {
        this.dateStart = constraintAnnotation.fieldStart();
        this.dateEnd = constraintAnnotation.fieldEnd();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDate start = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(dateStart);
        LocalDate end = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(dateEnd);
        if(start == null) {
            context.buildConstraintViolationWithTemplate("Please enter starting date").addPropertyNode("dateStart").addConstraintViolation();
            if(end == null) {
                context.buildConstraintViolationWithTemplate("Please enter ending date").addPropertyNode("dateEnd").addConstraintViolation();
            }
            return false;
        }

        LocalDate dateCurrent = LocalDate.now();
        if(start.isBefore(dateCurrent)) {
            String dateCurrentString = dateCurrent.toString();
            context.buildConstraintViolationWithTemplate("Project should begin after " + dateCurrentString).addPropertyNode("dateStart").addConstraintViolation();
            return false;
        }

        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addPropertyNode("dateEnd").addConstraintViolation();
        return start.isBefore(end);
    }
}
