package com.labospring.LaboFootApp.il.validators;

import com.labospring.LaboFootApp.il.annotation.FieldsValueMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
        Object fieldValue = wrapper.getPropertyValue(field);
        Object fieldMatchValue = wrapper.getPropertyValue(fieldMatch);

        // Use Objects.equals for null-safe comparison
        return Objects.equals(fieldValue, fieldMatchValue);
    }
}