package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.service.RefereeService;
import com.labospring.LaboFootApp.bll.service.models.RefereeBusiness;
import com.labospring.LaboFootApp.bll.specification.RefereeSpecification;
import com.labospring.LaboFootApp.dal.repositories.RefereeRepository;
import com.labospring.LaboFootApp.dl.entities.Referee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.List;
@Service
@RequiredArgsConstructor
public class RefereeServiceImpl implements RefereeService {

    private final RefereeRepository refereeRepository;

    @Override
    public Long addOne(RefereeBusiness entityBusiness) {
        return refereeRepository.save(entityBusiness.toEntity()).getId();
    }

    @Override
    public Referee getOne(Long id) {
        return refereeRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No Referee with ID : " + id));
    }

    @Override
    public List<Referee> getAll() {
        return refereeRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {
        Referee referee = getOne(id);
        refereeRepository.delete(referee);
    }

    @Override
    public void updateOne(Long id, RefereeBusiness entityBusiness) {
        Referee referee = getOne(id);
        referee.setFirstname(entityBusiness.firstname());
        referee.setLastname(entityBusiness.lastname());

        refereeRepository.save(referee);
    }

    @Override
    public List<Referee> getByCriteria(Referee coach){
        Specification<Referee> specification = Specification.where(null);

        if(coach.getLastname() != null && !coach.getLastname().isEmpty()){
            specification = specification.and(RefereeSpecification.hasLastname(coach.getLastname()));
        }

        if(coach.getFirstname() != null && !coach.getFirstname().isEmpty()){
            specification = specification.and(RefereeSpecification.hasFirstname(coach.getFirstname()));
        }

        return refereeRepository.findAll(specification);
    }
}
