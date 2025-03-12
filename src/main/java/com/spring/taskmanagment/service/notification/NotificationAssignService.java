package com.spring.taskmanagment.service.notification;

import com.spring.taskmanagment.dto.notification.NotificationAssign;
import com.spring.taskmanagment.service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationAssignService extends NotificationService<NotificationAssign> {

    private final UserService userService;

    private static final String DESTINATION = "/queue/notifications";

    public NotificationAssignService(UserService userService) {
        super(DESTINATION);
        this.userService = userService;
    }


    @EventListener
    @Override
    public void sendNotification(NotificationAssign response) {
        String userEmailReceiver = userService.findUserById(response.getUserReceiverId()).getEmail();
        sender(userEmailReceiver, response.getMessage());
    }
}
