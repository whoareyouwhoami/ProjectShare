package com.project.share.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ProjectDateDifferenceValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidateProjectDateDifference {
    String message() default "Invalid project duration";

    String fieldStart();

    String fieldEnd();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
