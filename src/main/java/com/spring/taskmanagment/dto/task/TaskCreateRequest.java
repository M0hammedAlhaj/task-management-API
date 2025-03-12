package com.spring.taskmanagment.dto.task;

import com.spring.taskmanagment.model.TaskPriority;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateRequest {

    @NotNull(message = "Task name is required")
    @Size(min = 3, max = 100, message = "Task name must be between 3 and 100 characters")
    private String taskName;

    @Size(max = 500, message = "Task description must not exceed 500 characters")
    private String taskDescription;

    private TaskPriority priority;

    private LocalDate dueDate;

}
