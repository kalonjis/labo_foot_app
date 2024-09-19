package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Getter
@Entity
@Table(name = "tournament",
        uniqueConstraints = @UniqueConstraint(name = "UK_title_startDate_address", columnNames = {"title", "startDate", "placeName"}))
public class Tournament extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;
    @Setter
    private LocalDateTime startDate;
    @Setter
    private LocalDateTime endDate;
    @Setter
    @Column(nullable = false)
    private String placeName;
    @Setter  @Embedded
    @Column(nullable = false)
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "tournament_type_list_by_tournament", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "tournament_id", nullable = false), // Clé étrangère pour l'entité actuelle (Tournament)
            inverseJoinColumns = @JoinColumn(name = "tournament_type_id", nullable = false) // Clé étrangère pour l'entité associée (TournamentType)
    )
    private Set<TournamentType> tournamentTypes;


}
