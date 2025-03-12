package com.spring.taskmanagment.dto.task;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class TaskAssignmentRequest {

    @NotNull(message = "Task ID is required")
    private Long taskId;

    @NotNull(message = "User assignment ID is required")
    private Long userAssignmentId;


}
