package com.spring.taskmanagment.controller;


import com.spring.taskmanagment.dto.notification.NotificationAssign;
import com.spring.taskmanagment.dto.task.TaskAssignmentRequest;
import com.spring.taskmanagment.dto.task.TaskAssignmentResponse;
import com.spring.taskmanagment.dto.task.TaskCreateRequest;
import com.spring.taskmanagment.dto.task.TaskResponse;
import com.spring.taskmanagment.model.TaskPriority;
import com.spring.taskmanagment.model.TaskStatus;
import com.spring.taskmanagment.service.TaskService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final ApplicationEventPublisher eventPublisher;
    private final TaskService taskService;


    public TaskController(ApplicationEventPublisher eventPublisher,
                          TaskService taskService) {
        this.eventPublisher = eventPublisher;
        this.taskService = taskService;
    }


    @PostMapping("/")
    public ResponseEntity<TaskResponse> taskResponseDtoResponseEntity
            (@RequestBody TaskCreateRequest taskCreateRequest
                    , @AuthenticationPrincipal UserDetails userOwner) {

        String userEmail = userOwner.getUsername();

        return ResponseEntity.ok(taskService.creatTaskByUserEmail(taskCreateRequest, userEmail));

    }

    @GetMapping("/")
    public ResponseEntity<List<TaskResponse>> getAllTasks(@RequestParam int pageNumber,
                                                          @RequestParam int pageSize,
                                                          @RequestParam(required = false) TaskStatus taskStatus,
                                                          @RequestParam(required = false) TaskPriority taskPriority,
                                                          @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(taskService.findAllTasks(pageNumber,
                pageSize,
                taskPriority,
                taskStatus,
                userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id,
                                                   @RequestBody TaskCreateRequest taskCreateRequest,
                                                   @AuthenticationPrincipal UserDetails userOwner) {

        String emailUser = userOwner.getUsername();
        return ResponseEntity.ok(taskService.updateTask(taskCreateRequest, id, emailUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id,
                                                @AuthenticationPrincipal UserDetails userOwner) {
        String userEmail = userOwner.getUsername();
        return ResponseEntity.ok(taskService.findTaskResponseById(id, userEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable Long id,
                                                   @AuthenticationPrincipal UserDetails userOwner) {
        String userEmail = userOwner.getUsername();
        return ResponseEntity.ok(taskService.deleteTaskById(id, userEmail));
    }


    @PutMapping("/assign")
    public ResponseEntity<TaskAssignmentResponse> taskAssignmentUser(@RequestBody TaskAssignmentRequest taskAssignmentRequest,
                                                                     @AuthenticationPrincipal UserDetails userOwner) {

        TaskAssignmentResponse taskAssignmentResponse = taskService.assignmentTaskToUser(taskAssignmentRequest, userOwner.getUsername());

        eventPublisher.publishEvent(new NotificationAssign(taskAssignmentRequest.getUserAssignmentId()));

        return ResponseEntity.ok(taskAssignmentResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(@RequestParam String taskName,
                                                          @AuthenticationPrincipal UserDetails user) {

        return ResponseEntity.ok(taskService.searchTasksByUserEmailAndTaskName(taskName, user.getUsername()));
    }
}
