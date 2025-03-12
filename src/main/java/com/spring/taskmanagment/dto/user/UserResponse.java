package com.spring.taskmanagment.dto.user;


import java.io.Serializable;


public class UserResponse extends UserCreateRequest implements Serializable {

    private Long userId;

    private UserCreateRequest user;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
