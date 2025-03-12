package com.spring.taskmanagment.dto.task;

import jakarta.validation.constraints.NotNull;

public class TaskAssignmentRequest {

    @NotNull(message = "Task ID is required")
    private Long taskId;

    @NotNull(message = "User assignment ID is required")
    private Long userAssignmentId;


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserAssignmentId() {
        return userAssignmentId;
    }

    public void setUserAssignmentId(Long userAssignmentId) {
        this.userAssignmentId = userAssignmentId;
    }
}
