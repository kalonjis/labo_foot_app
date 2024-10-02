package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dl.entities.Ranking;
import java.util.List;

public interface BestRankingFinderService {
    List<Ranking> findTopRankings(List<Ranking> rankings, int numberRanking);
}
