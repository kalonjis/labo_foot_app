package com.labospring.LaboFootApp.bll.security;


import com.labospring.LaboFootApp.dl.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    User register(User user);
    User login(String username, String password);
}
