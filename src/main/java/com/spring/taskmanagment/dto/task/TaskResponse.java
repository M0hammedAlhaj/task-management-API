package com.spring.taskmanagment.dto.task;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskResponse {

    private Long taskId;

    private TaskCreateRequest taskCreateRequest;

}
