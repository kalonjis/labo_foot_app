package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.team.TeamBusiness;
import com.labospring.LaboFootApp.dl.entities.Team;

public interface TeamService extends BaseService<Long, Team, TeamBusiness>, BaseByUser<Team> {
}
