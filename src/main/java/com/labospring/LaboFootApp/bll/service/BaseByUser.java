package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dl.entities.User;

import java.util.List;

public interface BaseByUser<T> {
    List<T> findAllByUser(User user);
}
