package com.labospring.LaboFootApp.pl.models.user;

import com.labospring.LaboFootApp.dl.entities.Address;
import com.labospring.LaboFootApp.dl.entities.User;

import java.time.LocalDate;

public record UserDTO(
        Long id,
        String username,
        String firstname,
        String lastname,
        String email,
        LocalDate birthdate,
        String phoneNumber,
        Address address
) {

    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getBirthdate(),
                user.getPhoneNumber(),
                user.getAddress() != null ? user.getAddress() : new Address()
        );
    }
}
