package com.spring.taskmanagment.model.entity;

import com.spring.taskmanagment.model.TaskPriority;
import com.spring.taskmanagment.model.TaskStatus;
import com.spring.taskmanagment.model.TimeLogType;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @NotBlank(message = "Task name must not be blank")
    @Size(max = 100, message = "Task name must not exceed 100 characters")
    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_owner_id")
    private User userOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_assign_id")
    private User userAssign;

    @OneToMany(mappedBy = "taskComment",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Set<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinTable(
            name = "time_log_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "time_log_id")
    )
    private Set<TimeLog> timeLogs = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        TimeLog timeLog = new TimeLog();
        timeLog.setType(TimeLogType.CREATE);
        timeLog.setDate(LocalDateTime.now());
        timeLogs.add(timeLog);
    }

    @PreUpdate
    protected void onUpdate() {
        TimeLog timeLog = new TimeLog();
        timeLog.setType(TimeLogType.UPDATE);
        timeLog.setDate(LocalDateTime.now());
        timeLogs.add(timeLog);
    }

    public Task() {

    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public User getUserAssign() {
        return userAssign;
    }

    public void setUserAssign(User userAssign) {
        this.userAssign = userAssign;
    }

    public Set<TimeLog> getTimeLogs() {
        return timeLogs;
    }

    public void setTimeLogs(Set<TimeLog> timeLogs) {
        this.timeLogs = timeLogs;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(taskId, task.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(taskId);
    }
}
