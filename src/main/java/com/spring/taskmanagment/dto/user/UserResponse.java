package com.spring.taskmanagment.dto.user;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


public class UserResponse extends UserCreateRequest implements Serializable {

    @Setter
    @Getter
    private Long userId;

    private UserCreateRequest user;

}
