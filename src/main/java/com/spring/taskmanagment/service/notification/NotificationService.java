package com.spring.taskmanagment.service.notification;


import com.spring.taskmanagment.dto.notification.NotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
abstract public class NotificationService<T extends NotificationResponse> {


    private static SimpMessagingTemplate simpMessagingTemplate;
    private String destination;

    private NotificationService() {
    }

    public NotificationService(String destination) {
        this.destination = destination;
    }

    public abstract void sendNotification(T response);


    protected void sender(String authenticatedUserEmail,
                          String message) {

        simpMessagingTemplate.convertAndSendToUser(authenticatedUserEmail,
                destination,
                message);
    }

    @Autowired
    public void setSimpMessagingTemplate(SimpMessagingTemplate simpMessagingTemplate) {
        NotificationService.simpMessagingTemplate = simpMessagingTemplate;
    }
}
