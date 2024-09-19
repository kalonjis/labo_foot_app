package com.labospring.LaboFootApp.bll.service;


import java.util.List;

public interface BaseService<Id, Entity, BusinessEntity> {
    Id addOne(BusinessEntity entityBusiness);

    Entity getOne(Id id);

    List<Entity> getAll();

    void deleteOne(Id id);

    void updateOne(Id id, BusinessEntity entityBusiness);

}
