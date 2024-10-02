package com.labospring.LaboFootApp.pl.models.user;

import com.labospring.LaboFootApp.bll.service.models.user.UserEditBusiness;
import com.labospring.LaboFootApp.dl.entities.Address;
import com.labospring.LaboFootApp.il.annotation.BeforeToday;
import com.labospring.LaboFootApp.il.constances.LaboFootConsts;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEditForm {
    @NotBlank @NotNull
    private String firstname;
    @NotBlank @NotNull
    private String lastname;
    @BeforeToday
    private LocalDate birthdate;
    @Pattern( regexp = LaboFootConsts.PHONE_REGEX, message = "Not a valid phone number")
    private String phoneNumber;
    private String street;
    private String city;
    private String zip;
    private String state;
    private String country;

    public UserEditBusiness toBusinessEntity(){
        return new UserEditBusiness(firstname, lastname, birthdate, phoneNumber, new Address(street, city, zip, state, country));
    }
}  