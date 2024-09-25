package com.labospring.LaboFootApp.dl.enums;

import lombok.Getter;


public enum MatchStage {
    GROUP_STAGE(0),
    FINAL(1),
    SEMI_FINAL(2),
    QUARTER_FINAL(4),
    ROUND_OF_16(8);

    @Getter
    private final int nbPositions;

    MatchStage(int nbPositions) {
        this.nbPositions = nbPositions;
    }

}
