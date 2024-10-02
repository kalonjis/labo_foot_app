package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.TournamentType;
import com.labospring.LaboFootApp.dl.enums.TournamentStatus;
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
    @Setter @Enumerated(EnumType.STRING)
    private TournamentStatus tournamentStatus;

    @Setter
    @ManyToOne
//    @JoinColumn(nullable = false, updatable = false)
    private User creator;


    @Setter
    @OneToMany(mappedBy = "tournament", cascade ={CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Ranking> rankingList;

    public Tournament(Long id, String title, LocalDateTime startDate, LocalDateTime endDate, String placeName, Address address, TournamentType tournamentType, TournamentStatus tournamentStatus) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeName = placeName;
        this.address = address;
        this.tournamentType = tournamentType;
        this.tournamentStatus = tournamentStatus;
        this.rankingList = new ArrayList<>();
    }

    public Tournament(String title, LocalDateTime startDate, LocalDateTime endDate, String placeName, Address address, TournamentType tournamentType, TournamentStatus tournamentStatus) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeName = placeName;
        this.address = address;
        this.tournamentType = tournamentType;
        this.tournamentStatus = tournamentStatus;
    }

    public Tournament(String title, LocalDateTime startDate, LocalDateTime endDate, String placeName, TournamentType tournamentType) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.placeName = placeName;
        this.tournamentType = tournamentType;
        this.tournamentStatus = TournamentStatus.BUILDING;
        this.rankingList = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tournament that = (Tournament) o;
        return tournamentStatus == that.tournamentStatus && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(placeName, that.placeName) && Objects.equals(address, that.address) && tournamentType == that.tournamentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, title, startDate, endDate, placeName, address, tournamentType, tournamentStatus);
    }
}
