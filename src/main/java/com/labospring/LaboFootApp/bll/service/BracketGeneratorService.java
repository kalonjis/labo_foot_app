package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStage;

import java.util.HashMap;
import java.util.List;

public interface BracketGeneratorService {
    List<Bracket> generateBrackets(Tournament tournament);
    HashMap<MatchStage, List<Bracket>> getBracketsByMatchStage(List<Bracket> brackets);

    MatchStage getFirstMatchStage(Tournament tournament);
}
