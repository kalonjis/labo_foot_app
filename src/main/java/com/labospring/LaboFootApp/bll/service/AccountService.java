package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.user.UserEditBusiness;
import com.labospring.LaboFootApp.bll.service.models.user.UserPasswordEditBusiness;
import com.labospring.LaboFootApp.dl.entities.User;

public interface AccountService {
    User get();
    void update(UserEditBusiness user);
    void delete();
    void changePassword(UserPasswordEditBusiness passwordEditBusiness);
}
