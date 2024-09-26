package com.labospring.LaboFootApp.bll.security.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.security.AuthService;
import com.labospring.LaboFootApp.dal.repositories.UserRepository;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("User with username " + user.getUsername() + " already exists");
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        User existingUser = (User) loadUserByUsername(username);

        if(!passwordEncoder.matches(password, existingUser.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return existingUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(
                () -> new DoesntExistsException("This user account is inactive or doesn't exists"));
    }
}
