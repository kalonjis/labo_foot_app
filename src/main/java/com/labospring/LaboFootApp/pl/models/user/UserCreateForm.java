package com.labospring.LaboFootApp.pl.models.user;

import com.labospring.LaboFootApp.dl.entities.Address;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.il.annotation.FieldsValueMatch;
import com.labospring.LaboFootApp.il.props.LaboFootProps;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldsValueMatch(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "Password do not match!"
)
public class UserCreateForm extends UserEditForm{
    @Email
    private String email;
    @NotNull
    @NotBlank
    @Size(min=3, max = 50)
    private String username;
    @NotBlank @NotNull
    @Pattern(regexp = LaboFootProps.PASSWORD_REGEX, message = "Password need minimum 7 characters with uppercase, lowercase, number and special characters")
    private String password;

    private String confirmPassword;

    public User toUser(){
        return new User(
                username,
                password,
                getFirstname(),
                getLastname(),
                getEmail(),
                getBirthdate(),
                getPhoneNumber(),
                new Address(getStreet(), getCity(), getZip(), getState(), getCountry()));
    }
}


