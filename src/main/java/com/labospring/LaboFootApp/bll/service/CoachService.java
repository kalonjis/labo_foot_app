package com.labospring.LaboFootApp.bll.service;


import com.labospring.LaboFootApp.bll.service.models.CoachBusiness;
import com.labospring.LaboFootApp.dl.entities.Coach;

import java.util.List;


public interface CoachService extends BaseService<Long, Coach, CoachBusiness>{
    List<Coach> getByCriteria(Coach coach);
}
