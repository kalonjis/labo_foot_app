package com.labospring.LaboFootApp.dl.entities;

import com.labospring.LaboFootApp.dl.enums.MatchStage;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Bracket extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @Setter
    private Tournament tournament;

    @OneToOne
    @JoinColumn(nullable = false)
    @Setter
    private FootMatch match;

    @Setter
    private Integer positionBracket;

    @Enumerated(EnumType.STRING)
    @Setter
    private MatchStage matchStage;

}

