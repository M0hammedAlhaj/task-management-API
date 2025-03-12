package com.spring.taskmanagment.dto.comment;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class CommentCreationRequest implements Serializable {

    private Long taskId;

    @NotNull
    private String content;


    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
