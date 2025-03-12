package com.spring.taskmanagment.service;


import com.spring.taskmanagment.dao.UserDao;
import com.spring.taskmanagment.dto.user.UserCreateRequest;
import com.spring.taskmanagment.dto.user.UserResponse;
import com.spring.taskmanagment.dto.user.UserResponseAdmin;
import com.spring.taskmanagment.exception.RegistrationException;
import com.spring.taskmanagment.exception.ResourceNotFoundException;
import com.spring.taskmanagment.mapper.UserMap;
import com.spring.taskmanagment.model.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;

    private final RoleService roleService;


    private final PasswordEncoder passwordEncoder;

    private final UserMap userMap;

    public UserService(UserDao userDao,
                       RoleService roleService,
                       PasswordEncoder passwordEncoder,
                       UserMap userMap) {

        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMap = userMap;
    }

    @Cacheable(value = "user", key = "#email")
    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Transactional
    @CacheEvict(value = "users")
    @CachePut(value = "user", key = "#userCreateRequest.email")
    public UserResponse saveUser(UserCreateRequest userCreateRequest) {


        if (findUserByEmail(userCreateRequest.getEmail()).isPresent()) {
            throw new RegistrationException("User existed By Email "
                    + userCreateRequest.getEmail());
        }

        User user = userMap.userCreationDtoToUser(userCreateRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleService.findRoleByName("ROLE_USER"));

        return userMap.userToUserResponseUser(userDao.save(user));
    }

    @Cacheable(value = "users")
    public List<UserResponseAdmin> findAllUsers() {
        return userDao.findAll()
                .stream()
                .map(userMap::userToUserResponseAdmin)
                .toList();
    }

    @Cacheable(value = "user", key = "#id")
    public UserResponse findUserResponseById(Long id) {
        return userMap.userToUserResponseUser(userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found")));
    }

    @Cacheable(value = "user", key = "#id")
    public User findUserById(Long id) {
        return userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Transactional
    @CacheEvict(value = "users")
    @CachePut(value = "user", key = "#userEmail")
    public UserResponse updateUser(UserCreateRequest userCreateRequestToUpdate,
                                   String userEmail) {

        if (userCreateRequestToUpdate == null) {
            throw new ResourceNotFoundException("User not found");
        }

        User user = userDao.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));


        user.setPassword(passwordEncoder.encode(userCreateRequestToUpdate.getPassword()));
        user.setEmail(userCreateRequestToUpdate.getEmail());
        user.setUserName(userCreateRequestToUpdate.getUserName());

        return userMap.userToUserResponseUser(userDao.save(user));
    }

    @Transactional
    @Caching(evict = {@CacheEvict(value = "users"),
            @CacheEvict(value = "user", key = "#id")})
    public UserResponse deleteUserById(Long id) {
        User deleted = userDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        userDao.deleteById(id);
        return userMap.userToUserResponseUser(deleted);
    }

    public Long userCreateCount(LocalDateTime startDate, LocalDateTime endDate) {
        return userDao.findUsersCreationCount(startDate, endDate);
    }

}
