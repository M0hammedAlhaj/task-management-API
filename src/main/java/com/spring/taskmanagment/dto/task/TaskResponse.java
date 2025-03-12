package com.spring.taskmanagment.dto.task;

public class TaskResponse {

    private Long taskId;

    private TaskCreateRequest taskCreateRequest;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public TaskCreateRequest getTaskCreateRequest() {
        return taskCreateRequest;
    }

    public void setTaskCreateRequest(TaskCreateRequest taskCreateRequest) {
        this.taskCreateRequest = taskCreateRequest;
    }
}
