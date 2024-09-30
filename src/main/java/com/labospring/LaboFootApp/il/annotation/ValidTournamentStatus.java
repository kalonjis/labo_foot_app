package com.labospring.LaboFootApp.il.annotation;

import com.labospring.LaboFootApp.il.validators.TournamentStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TournamentStatusValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTournamentStatus {
    String message() default "Invalid Tournament Status. Accepted values are: [BUILDING, PENDING, STARTED, INTERRUPTED, CLOSED, CANCELED]";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}