package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.RefereeBusiness;
import com.labospring.LaboFootApp.dl.entities.Referee;

import java.util.List;

public interface RefereeService extends BaseService<Long, Referee, RefereeBusiness> {
    List<Referee> getByCriteria(Referee referee);
}
