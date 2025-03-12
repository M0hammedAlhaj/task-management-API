package com.spring.taskmanagment.service.notification;

import com.spring.taskmanagment.dto.notification.NotificationCommentResponse;
import com.spring.taskmanagment.exception.ResourceNotFoundException;
import com.spring.taskmanagment.model.entity.Task;
import com.spring.taskmanagment.model.entity.User;
import com.spring.taskmanagment.service.TaskService;
import com.spring.taskmanagment.service.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationCommentService extends NotificationService<NotificationCommentResponse> {


    private static final String DESTINATION = "/queue/notifications";

    private final TaskService taskService;

    private final UserService userService;

    public NotificationCommentService(TaskService taskService, UserService userService) {
        super(DESTINATION);
        this.taskService = taskService;
        this.userService = userService;
    }

    @EventListener
    @Transactional
    @Override
    public void sendNotification(NotificationCommentResponse response) {

        Task task = taskService.findTaskById(response.getTaskId());
        Long userAuthorId = userService.findUserByEmail(response.getAuthorEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found by email")).getUserId();

        User userOwner = task.getUserOwner();

        User userParticipated = task.getUserAssign();
        if (userParticipated == null) {
            return;
        }
        if (userOwner.getUserId().equals(userAuthorId)) {
            sender(userParticipated.getEmail(), response.getMessage());
            return;
        }
        sender(userOwner.getEmail(), response.getMessage());

    }
}
