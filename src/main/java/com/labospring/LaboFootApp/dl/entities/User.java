package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false) @ToString
@Getter
@Entity
@Table(name = "user_")
public class User extends BaseEntity implements UserDetails {

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
    @Setter @Column(unique = true)
    private String email;
    @Setter @Temporal(TemporalType.DATE)
    private LocalDate birthdate;
    @Setter @Column(unique = true, length = 25)
    private String phoneNumber;
    @Setter @Embedded
    private Address address;

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String firstname, String lastname, String email, LocalDate birthdate, String phoneNumber, Address address) {
        this(firstname, lastname, birthdate, phoneNumber, address);
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String firstname, String lastname, LocalDate birthdate, String phoneNumber, Address address) {
        this.firstname = firstname;
        this.lastname = lastname;

        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
