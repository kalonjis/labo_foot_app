package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.service.CoachService;
import com.labospring.LaboFootApp.bll.service.models.CoachBusiness;
import com.labospring.LaboFootApp.dal.repositories.CoachRepository;
import com.labospring.LaboFootApp.dl.entities.Coach;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;

    @Override
    public Long addOne(CoachBusiness entityBusiness) {
        return coachRepository.save(entityBusiness.toEntity()).getId();
    }

    @Override
    public Coach getOne(Long id) {
        return coachRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No coach with ID : " + id));
    }

    @Override
    public List<Coach> getAll() {
        return coachRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {
        Coach coach = getOne(id);
        coachRepository.delete(coach);
    }

    @Override
    public void updateOne(Long id, CoachBusiness entityBusiness) {
        Coach coach = getOne(id);
        coach.setFirstname(entityBusiness.firstname());
        coach.setLastname(entityBusiness.lastname());

        coachRepository.save(coach);
    }
}
