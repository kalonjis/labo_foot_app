package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode @ToString
@Getter
@Entity
@Table(name = "user_")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(unique = true, nullable = false, length = 50)
    private String username;
    @Setter @Column(nullable = false)
    private String password;
    @Setter @Column(nullable = false, length = 50)
    private String firstname;
    @Setter @Column(nullable = false, length = 50)
    private String lastname;
    @Setter
    private String email;
    @Setter @Temporal(TemporalType.DATE)
    private LocalDate birthdate;
    @Setter @Column(unique = true, length = 25)
    private String phoneNumber;
    @Setter @Embedded
    private Address address;

    @Setter
    private Role role;

}
