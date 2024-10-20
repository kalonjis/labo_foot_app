package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.bll.service.models.user.PasswordResetFormBusiness;
import com.labospring.LaboFootApp.bll.service.models.user.UserEditBusiness;
import com.labospring.LaboFootApp.bll.service.models.user.UserPasswordEditBusiness;
import com.labospring.LaboFootApp.dl.entities.User;

import java.util.List;

public interface UserService extends BaseService<Long, User, User>{
    void updateOne(Long id, UserEditBusiness entityBusiness);
    void updatePassword(Long id, UserPasswordEditBusiness passwordEditBusiness);
    void resetPassword(Long id, PasswordResetFormBusiness passwordResetFormBusiness);
    List<User> findAllByUsername(String username);
    List<User> getByCriteria(User user);
    List<User> getUsersForMatchAndTeamsWithNotifications(Long matchId, List<Long> teamIds);
    void enableUser(User user);
    void disableUser(User user);
}
