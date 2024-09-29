package com.labospring.LaboFootApp.il.validators;

import com.labospring.LaboFootApp.il.annotation.BeforeToday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BeforeTodayValidator implements ConstraintValidator<BeforeToday, LocalDate> {
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null) return true;
        return date.isBefore(LocalDate.now());
    }
}
