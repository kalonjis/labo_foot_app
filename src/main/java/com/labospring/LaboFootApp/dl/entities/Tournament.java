package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "UK_title_startDate_address", columnNames = {"title", "startDate", "address"}))
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;
    @Setter @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startDate;
    @Setter @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endDate;
    @Setter  @Embedded
    @Column(nullable = false)
    private Address address;

}
