package com.spring.taskmanagment.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spring.taskmanagment.model.entity.Role;

import java.util.Set;

@JsonSerialize
public class UserResponseAdmin {

    private Long userId;

    private UserCreateRequest user;

    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserDtoForAdmin{" +
                "userId=" + userId +
                ", roles=" + roles +
                '}';
    }
}
