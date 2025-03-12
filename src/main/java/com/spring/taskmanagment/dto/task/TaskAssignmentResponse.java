package com.spring.taskmanagment.dto.task;

public class TaskAssignmentResponse {

    private TaskResponse taskResponse;

    private String userAssigmentName;

    private String userOwnerName;

    public TaskResponse getTaskResponse() {
        return taskResponse;
    }

    public void setTaskResponse(TaskResponse taskResponse) {
        this.taskResponse = taskResponse;
    }


    public String getUserAssigmentName() {
        return userAssigmentName;
    }

    public void setUserAssigmentName(String userAssigmentName) {
        this.userAssigmentName = userAssigmentName;
    }

    public String getUserOwnerName() {
        return userOwnerName;
    }

    public void setUserOwnerName(String userOwnerName) {
        this.userOwnerName = userOwnerName;
    }
}
