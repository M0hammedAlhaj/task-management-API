package com.spring.taskmanagment.mapper;

import com.spring.taskmanagment.dto.user.UserCreateRequest;
import com.spring.taskmanagment.dto.user.UserResponse;
import com.spring.taskmanagment.dto.user.UserResponseAdmin;
import com.spring.taskmanagment.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMap {


//    @Mapping(target = "userName", source = "user.userName")
//    @Mapping(target = "email", source = "user.email")
//    @Mapping(target = "password", source = "user.password")
//    UserCreateRequest userToUserCreateRequest(User user);

    UserCreateRequest userToUserCreateRequest(User user);

    @Mapping(target = "userName", source = "userCreateRequest.userName")
    @Mapping(target = "email", source = "userCreateRequest.email")
    @Mapping(target = "password", source = "userCreateRequest.password")
    User userCreationDtoToUser(UserCreateRequest userCreateRequest);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "roles", source = "user.roles")
    UserResponseAdmin userToUserResponseAdmin(User user);

    @Mapping(target = "userId", source = "user.userId")
    UserResponse userToUserResponseUser(User user);
}
