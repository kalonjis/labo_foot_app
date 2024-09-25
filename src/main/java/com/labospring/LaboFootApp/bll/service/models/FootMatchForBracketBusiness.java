package com.labospring.LaboFootApp.bll.service.models;

import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.MatchStage;


public record FootMatchForBracketBusiness(Tournament tournament,
                                          MatchStage matchStage) {

}
