package com.spring.taskmanagment.dto.comment;

import lombok.*;

import java.io.Serializable;

@Data
public class CommentResponse implements Serializable {

    private String content;

    private String authorName;

    private String taskName;

    private Long commentId;

    private Long taskId;

}
