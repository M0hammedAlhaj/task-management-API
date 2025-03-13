package com.spring.taskmanagment.service;

import com.spring.taskmanagment.dao.TaskDao;
import com.spring.taskmanagment.dto.task.TaskAssignmentRequest;
import com.spring.taskmanagment.dto.task.TaskAssignmentResponse;
import com.spring.taskmanagment.dto.task.TaskCreateRequest;
import com.spring.taskmanagment.dto.task.TaskResponse;
import com.spring.taskmanagment.exception.ResourceNotFoundException;
import com.spring.taskmanagment.exception.TaskException;
import com.spring.taskmanagment.mapper.TaskMap;
import com.spring.taskmanagment.model.TaskPriority;
import com.spring.taskmanagment.model.TaskStatus;
import com.spring.taskmanagment.model.entity.Task;
import com.spring.taskmanagment.model.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public class TaskService {

    private final TaskDao taskDao;

    private final UserService userService;

    private final TaskMap taskMap;


    public TaskService(TaskDao taskDao, UserService userService, TaskMap taskMap) {

        this.taskDao = taskDao;
        this.userService = userService;
        this.taskMap = taskMap;
    }


    @Transactional
    public TaskResponse creatTaskByUserEmail(TaskCreateRequest taskCreateRequest,
                                             String userEmail) {

        User user = userService.findUserByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        Task task = taskMap.taskCreateRequestToTask(taskCreateRequest);

        task.setUserOwner(user);
        taskDao.save(task);
        return taskMap.taskToTaskResponse(task);
    }

    public List<TaskResponse> findAllTasks(int pageNumber,
                                           int pageSize,
                                           TaskPriority taskPriority,
                                           TaskStatus taskStatus,
                                           String userEmail) {


        if (pageNumber < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("page number or page size is invalid");
        }

        User user = userService.findUserByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        TaskFilterSpecification taskFilterSpecification = new TaskFilterSpecification(taskPriority, taskStatus, user.getUserId());

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Task> page = taskDao.findAll(taskFilterSpecification, pageable);


        return page.stream().map(taskMap::taskToTaskResponse).toList();
    }

    @Transactional
    public TaskResponse updateTask(TaskCreateRequest taskCreateRequest, Long taskId, String userEmail) {

        Task taskUpdate = validateUserOwnership(taskId, userEmail);

        taskUpdate.setTaskName(taskCreateRequest.getTaskName());
        taskUpdate.setDescription(taskCreateRequest.getTaskDescription());
        taskUpdate.setPriority(taskCreateRequest.getPriority());
        taskUpdate.setDueDate(taskCreateRequest.getDueDate());

        taskUpdate = taskDao.save(taskUpdate);

        return taskMap.taskToTaskResponse(taskUpdate);
    }

    @Cacheable(value = "tasks", key = "{#id,#userEmail}")
    @Transactional
    public TaskResponse findTaskResponseById(Long id, String userEmail) {
        return taskMap.taskToTaskResponse(validateUserParticipated(id, userEmail));
    }

    @Transactional
    public Task validateUserParticipated(Long taskId, String userEmail) {

        Task task = taskDao.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("task not found"));

        User user = userService.findUserByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        boolean isParticipated = task.getUserAssign() != null && task.getUserAssign().equals(user);

        if (!isParticipated && !task.getUserOwner().equals(user)) {
            throw new AuthorizationServiceException("You are not participated in this task");
        }
        return task;
    }

    @Transactional
    public Task validateUserOwnership(Long taskId, String userEmail) {
        Task task = taskDao.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("task not found"));

        User user = userService.findUserByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        if (!task.getUserOwner().equals(user)) {
            throw new AuthorizationServiceException("The User not owned task");
        }
        return task;
    }


    @Transactional
    public TaskResponse deleteTaskById(Long taskId, String userEmil) {
        Task task = validateUserOwnership(taskId, userEmil);
        taskDao.delete(task);
        return taskMap.taskToTaskResponse(task);
    }

    @Transactional
    public TaskAssignmentResponse assignmentTaskToUser(TaskAssignmentRequest taskAssignmentRequest, String email) {

        Task task = validateUserOwnership(taskAssignmentRequest.getTaskId(), email);

        User user = userService.findUserById(taskAssignmentRequest.getUserAssignmentId());

        if (task.getUserAssign() != null) throw new TaskException("Task has a user assigned");

        task.setUserAssign(user);
        taskDao.save(task);

        return taskMap.taskToTaskAssignmentResponse(task);
    }

    @Cacheable(value = "task", key = "#taskId")
    @Transactional
    public Task findTaskById(Long taskId) {
        return taskDao.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("task not found"));
    }

    public Long countStatusTaskByUser(TaskStatus taskStatus, String userEmail) {
        return taskDao.countTaskByUserEmailAndTaskStatus(userEmail, taskStatus)
                .orElseThrow(() -> new ResourceNotFoundException("Users dont have tasks  "));
    }

    public HashMap<Long, Long> findTaskCountsPerUserAssignByOwnerEmailAndStatusNot(TaskStatus taskStatus, String userEmail) {
        return taskDao.findTaskCountsPerUserAssignByOwnerEmailAndStatusNot(userEmail, taskStatus)
                .orElseThrow(() -> new ResourceNotFoundException("Users dont have tasks "));
    }

    public BigDecimal calculateTaskRatByUserEmailAndTaskStatus(String userEmail,
                                                               TaskStatus taskStatus) {
        return taskDao.calculateTaskRatByUserEmailAndTaskStatus(userEmail, taskStatus);
    }
}
