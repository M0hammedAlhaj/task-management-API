package com.spring.taskmanagment.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
public class CommentCreationRequest implements Serializable {

    private Long taskId;

    @NotNull
    private String content;


}
