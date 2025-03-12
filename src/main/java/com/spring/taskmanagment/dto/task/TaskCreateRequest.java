package com.spring.taskmanagment.dto.task;

import com.spring.taskmanagment.model.TaskPriority;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TaskCreateRequest {

    @NotNull
    private String taskName;

    private String taskDescription;

    private TaskPriority priority;

    private LocalDate dueDate;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
