package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.service.UserService;
import com.labospring.LaboFootApp.bll.service.models.user.UserEditBusiness;
import com.labospring.LaboFootApp.bll.service.models.user.UserPasswordEditBusiness;
import com.labospring.LaboFootApp.bll.specification.UserSpecification;
import com.labospring.LaboFootApp.dal.repositories.UserRepository;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long addOne(User user) {
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    @Override
    public User getOne(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new DoesntExistsException("No User with ID " + id));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteOne(Long id) {
        userRepository.delete(getOne(id));
    }

    @Override
    public void updateOne(Long aLong, User entityBusiness) {}

    @Override
    public void updateOne(Long id, UserEditBusiness entityBusiness) {
        User user = getOne(id);

        user.setFirstname(entityBusiness.firstname());
        user.setLastname(entityBusiness.lastname());
        user.setBirthdate(entityBusiness.birthdate());
        user.setPhoneNumber(entityBusiness.phoneNumber());
        user.setAddress(entityBusiness.address());

        userRepository.save(user);
    }

    @Override
    public void updatePassword(Long id, UserPasswordEditBusiness passwordEditBusiness) {
        User user = getOne(id);
        if(!passwordEncoder.matches(passwordEditBusiness.oldPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        user.setPassword(passwordEncoder.encode(passwordEditBusiness.newPassword()));

        userRepository.save(user);
    }

    @Override
    public List<User> findAllByUsername(String username) {
        Specification<User> userSpecification = Specification.where(null);

        if (username != null) {
            userSpecification = userSpecification.and(UserSpecification.hasUsername(username));
        }
        return userRepository.findAll(userSpecification);
    }

    @Override
    public List<User> getByCriteria(User user) {
        Specification<User> userSpecification = Specification.where(null);

        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            userSpecification = userSpecification.and(UserSpecification.hasUsername(user.getUsername()));
        }

        if (user.getFirstname() != null && !user.getFirstname().isEmpty()) {
            userSpecification = userSpecification.and(UserSpecification.hasFirstname(user.getFirstname()));
        }

        if (user.getLastname() != null && !user.getLastname().isEmpty()) {
            userSpecification = userSpecification.and(UserSpecification.hasLastname(user.getLastname()));
        }

        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            userSpecification = userSpecification.and(UserSpecification.hasEmail(user.getEmail()));
        }

        return userRepository.findAll(userSpecification);
    }
}
