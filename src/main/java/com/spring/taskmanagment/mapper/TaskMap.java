package com.spring.taskmanagment.mapper;

import com.spring.taskmanagment.dto.task.TaskAssignmentResponse;
import com.spring.taskmanagment.dto.task.TaskCreateRequest;
import com.spring.taskmanagment.dto.task.TaskResponse;
import com.spring.taskmanagment.model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMap {


    @Mapping(target = "taskName", source = "taskCreateRequest.taskName")
    @Mapping(target = "description", source = "taskCreateRequest.taskDescription")
    @Mapping(target = "priority", source = "taskCreateRequest.priority")
    @Mapping(target = "dueDate", source = "taskCreateRequest.dueDate")
    Task taskCreateRequestToTask(TaskCreateRequest taskCreateRequest);

    @Mapping(target = "taskId", source = "task.taskId")
    @Mapping(target = "taskCreateRequest.taskName", source = "task.taskName")
    @Mapping(target = "taskCreateRequest.taskDescription", source = "task.description")
    @Mapping(target = "taskCreateRequest.priority", source = "task.priority")
    @Mapping(target = "taskCreateRequest.dueDate", source = "task.dueDate")
    TaskResponse taskToTaskResponse(Task task);


    @Mapping(target = "taskResponse.taskId", source = "task.taskId")
    @Mapping(target = "taskResponse.taskCreateRequest.taskName", source = "task.taskName")
    @Mapping(target = "taskResponse.taskCreateRequest.taskDescription", source = "task.description")
    @Mapping(target = "taskResponse.taskCreateRequest.priority", source = "task.priority")
    @Mapping(target = "taskResponse.taskCreateRequest.dueDate", source = "task.dueDate")
    @Mapping(target = "userAssigmentName", source = "task.userAssign.userName")
    @Mapping(target = "userOwnerName", source = "task.userOwner.userName")
    TaskAssignmentResponse taskToTaskAssignmentResponse(Task task);

}
