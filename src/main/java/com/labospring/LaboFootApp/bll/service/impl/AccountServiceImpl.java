package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.AccountService;
import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.bll.service.models.user.UserEditBusiness;
import com.labospring.LaboFootApp.bll.service.models.user.UserPasswordEditBusiness;
import com.labospring.LaboFootApp.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final UserService userService;
    @Override
    public User get() {
        return userService.getOne(getUser().getId());
    }

    @Override
    public void update(UserEditBusiness user) {
        userService.updateOne(getUser().getId(), user);
    }

    @Override
    public void delete() {
        userService.deleteOne(getUser().getId());
    }

    @Override
    public void changePassword(UserPasswordEditBusiness passwordEditBusiness) {
        userService.updatePassword(getUser().getId(), passwordEditBusiness);
    }

    public User getUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
