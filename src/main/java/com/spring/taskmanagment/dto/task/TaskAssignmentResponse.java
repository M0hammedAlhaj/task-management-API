package com.spring.taskmanagment.dto.task;

import lombok.*;

@Data
public class TaskAssignmentResponse {

    private TaskResponse taskResponse;

    private String userAssigmentName;

    private String userOwnerName;


}
