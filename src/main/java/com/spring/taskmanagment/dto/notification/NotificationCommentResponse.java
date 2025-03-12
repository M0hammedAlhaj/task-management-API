package com.spring.taskmanagment.dto.notification;

import lombok.Getter;

@Getter
public class NotificationCommentResponse implements NotificationResponse {

    private Long taskId;

    private String message;

    private String authorEmail;

    public NotificationCommentResponse(Long taskId, String authorEmail) {
        this.authorEmail=authorEmail;
        this.taskId = taskId;
        message = "";
    }

}
