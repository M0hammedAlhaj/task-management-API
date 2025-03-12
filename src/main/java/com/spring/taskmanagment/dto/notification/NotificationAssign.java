package com.spring.taskmanagment.dto.notification;

public class NotificationAssign implements NotificationResponse {

    private Long userReceiverId;

    private String message;

    public NotificationAssign(Long userReceiverId) {
        this.userReceiverId = userReceiverId;
        this.message = "A new task has been assigned to you!";
    }

    public Long getUserReceiverId() {
        return userReceiverId;
    }

    public String getMessage() {
        return message;
    }
}
