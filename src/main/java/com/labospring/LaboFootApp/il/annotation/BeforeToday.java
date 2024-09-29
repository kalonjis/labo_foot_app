package com.labospring.LaboFootApp.il.annotation;

import com.labospring.LaboFootApp.il.validators.BeforeTodayValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BeforeTodayValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeToday {
    String message() default "Date must be before today";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
