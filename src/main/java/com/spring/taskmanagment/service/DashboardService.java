package com.spring.taskmanagment.service;

import com.spring.taskmanagment.controller.UserController;
import com.spring.taskmanagment.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;

@Service
public class DashboardService {

    private final UserService userService;

    private final TaskService taskService;
    private final UserController userController;

    public DashboardService(UserService userService,
                            TaskService taskService, UserController userController) {

        this.userService = userService;
        this.taskService = taskService;
        this.userController = userController;
    }

    public Long countUsersCreatedMonthFromDay() {
        LocalDateTime today = LocalDateTime.of(LocalDate.now().getYear(), Month.MARCH, 1, 0, 0);
        LocalDateTime afterMonth = today.plusMonths(1);
        return userService.userCreateCount(today, afterMonth);
    }

    public Long countTaskByStatus(TaskStatus taskStatus, String userEmail) {
        return taskService.countStatusTaskByUser(taskStatus, userEmail);
    }

    public HashMap<Long, Long> findTaskCountsPerUserAssignByOwnerEmailAndStatusNotCompleted(String userEmail) {
        return taskService.findTaskCountsPerUserAssignByOwnerEmailAndStatusNot(TaskStatus.COMPLETED,
                userEmail);
    }

    public BigDecimal taskCompletedRatByUserEmail(String userEmail) {
        return taskService.calculateTaskRatByUserEmailAndTaskStatus(userEmail, TaskStatus.COMPLETED);
    }

}
