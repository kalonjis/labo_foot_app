package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.TournamentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
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
    @Setter
    @Enumerated(EnumType.STRING)
    private TournamentType tournamentType;
    @Setter
    private boolean isClose;

    @Setter
    @OneToMany(mappedBy = "tournament", cascade ={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Ranking> rankingList;

    public Tournament(Long id, String title, LocalDateTime startDate, LocalDateTime endDate, String placeName, Address address, TournamentType tournamentType, boolean isClose) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeName = placeName;
        this.address = address;
        this.tournamentType = tournamentType;
        this.isClose = isClose;
        this.rankingList = new ArrayList<>();
    }

    public Tournament(String title, LocalDateTime startDate, LocalDateTime endDate, String placeName, Address address, TournamentType tournamentType, boolean isClose) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeName = placeName;
        this.address = address;
        this.tournamentType = tournamentType;
        this.isClose = isClose;
    }

    //    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinTable(
//            name = "tournament_type_list_by_tournament", // Nom de la table de jointure
//            joinColumns = @JoinColumn(name = "tournament_id", nullable = false), // Clé étrangère pour l'entité actuelle (Tournament)
//            inverseJoinColumns = @JoinColumn(name = "tournament_type_id", nullable = false) // Clé étrangère pour l'entité associée (TournamentType)
//    )
//    private Set<TournamentType> tournamentTypes;

    public void addRanking(Ranking ranking){
        rankingList.add(ranking);
        ranking.setTournament(this);
    }

    public void removeRanking(Ranking ranking){
        rankingList.remove(ranking);
        ranking.setTournament(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tournament that = (Tournament) o;
        return isClose == that.isClose && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(placeName, that.placeName) && Objects.equals(address, that.address) && tournamentType == that.tournamentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, title, startDate, endDate, placeName, address, tournamentType, isClose);
    }
}
