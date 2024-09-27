package com.labospring.LaboFootApp.pl.models.user;

import com.labospring.LaboFootApp.dl.entities.Address;
import com.labospring.LaboFootApp.dl.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record UserForm (
    @NotNull
    @NotBlank
    @Size(min=3, max = 50)
    String username,
    @NotBlank @NotNull @Size(min=3)
    String password,
    @NotBlank @NotNull
    String firstname,
    @NotBlank @NotNull
    String lastname,
    @Email
    String email,
    LocalDate birthdate,
    String phoneNumber,
    String street,
    String city,
    String zip,
    String state,
    String country
    ){

    public User toUser(){
        return new User(username, password, firstname, lastname, email, birthdate, phoneNumber, new Address(street, city, zip, state, country));
    }
}
