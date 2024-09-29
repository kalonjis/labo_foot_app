package com.labospring.LaboFootApp.bll.service.models.user;

import com.labospring.LaboFootApp.dl.entities.Address;
import com.labospring.LaboFootApp.dl.entities.User;

import java.time.LocalDate;

public record UserEditBusiness(String firstname, String lastname, LocalDate birthdate, String phoneNumber, Address address) {

    public User toEntity() {
        return new User(firstname, lastname, birthdate, phoneNumber, address);
    }

}
