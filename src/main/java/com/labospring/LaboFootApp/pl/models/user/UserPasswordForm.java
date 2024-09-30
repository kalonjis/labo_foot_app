package com.labospring.LaboFootApp.pl.models.user;

import com.labospring.LaboFootApp.bll.service.models.user.UserPasswordEditBusiness;
import com.labospring.LaboFootApp.il.annotation.FieldsValueMatch;
import com.labospring.LaboFootApp.il.props.LaboFootProps;
import jakarta.validation.constraints.Pattern;

@FieldsValueMatch(
        field = "newPassword",
        fieldMatch = "confirmPassword",
        message = "Password do not match!"
)
public record UserPasswordForm(
        String oldPassword,
        @Pattern(regexp = LaboFootProps.PASSWORD_REGEX, message = "Password needs at least 7 characters with uppercase, lowercase, number, and special characters")
        String newPassword,
        String confirmPassword
) {
    public UserPasswordEditBusiness toBusinessEntity(){
        return new UserPasswordEditBusiness(oldPassword, newPassword);
    }
}
