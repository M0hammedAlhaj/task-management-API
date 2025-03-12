package com.spring.taskmanagment.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.taskmanagment.model.entity.Role;
import lombok.Data;

import java.util.Set;

@Data

@JsonSerialize
public class UserResponseAdmin {

    private Long userId;

    private UserCreateRequest user;

    private Set<Role> roles;

}
