package com.spring.taskmanagment.dto.task;

public class TaskAssignmentRequest {

    private Long taskId;

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
