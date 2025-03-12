package com.spring.taskmanagment.dto.notification;

public class NotificationCommentResponse implements NotificationResponse {

    private Long taskId;

    private String message;

    private String authorEmail;

    public NotificationCommentResponse(Long taskId, String authorEmail) {
        this.authorEmail=authorEmail;
        this.taskId = taskId;
        message = "";
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getMessage() {
        return message;
    }

    public String  getAuthorEmail() {
        return authorEmail;
    }
}
