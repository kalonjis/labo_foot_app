package com.labospring.LaboFootApp.pl.models.user;

import com.labospring.LaboFootApp.bll.service.models.user.PasswordResetFormBusiness;
import com.labospring.LaboFootApp.il.annotation.FieldsValueMatch;
import com.labospring.LaboFootApp.il.props.LaboFootProps;
import jakarta.validation.constraints.Pattern;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "Password do not match!"
)
public record PasswordResetForm(
        @Pattern(regexp = LaboFootProps.PASSWORD_REGEX, message = "Password needs at least 7 characters with uppercase, lowercase, number, and special characters")
        String password,
        String confirmPassword
        ) {
    public PasswordResetFormBusiness toBusiness(){
        return new PasswordResetFormBusiness(password);
    }
}

