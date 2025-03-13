package com.spring.taskmanagment.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

    private Long taskId;

    private TaskCreateRequest taskCreateRequest;

}
