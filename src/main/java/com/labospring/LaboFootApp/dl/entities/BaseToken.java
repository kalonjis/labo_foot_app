package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String token;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Setter
    @Column(nullable = false)
    private LocalDateTime expiryDate;
}
