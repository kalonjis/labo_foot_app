package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dl.entities.Bracket;
import com.labospring.LaboFootApp.dl.entities.Team;

import java.util.List;

public interface FootMatchBracketGeneratorService {
    void generateFootMatch(List<Bracket> brackets, List<Team> teams);
}
