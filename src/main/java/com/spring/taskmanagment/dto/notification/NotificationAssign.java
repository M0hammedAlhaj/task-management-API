package com.spring.taskmanagment.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NotificationAssign implements NotificationResponse {

    @NotNull(message = "User receiver ID is required")
    private Long userReceiverId;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    public NotificationAssign(Long userReceiverId) {
        this.userReceiverId = userReceiverId;
        this.message = "A new task has been assigned to you!";
    }

}
