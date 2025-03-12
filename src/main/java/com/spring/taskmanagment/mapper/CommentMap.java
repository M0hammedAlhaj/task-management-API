package com.spring.taskmanagment.mapper;

import com.spring.taskmanagment.dto.comment.CommentResponse;
import com.spring.taskmanagment.model.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMap {

    @Mapping(target = "content", source = "comment.content")
    @Mapping(target = "authorName", source = "comment.userAuthor.userName")
    @Mapping(target = "taskName", source = "comment.taskComment.taskName")
    @Mapping(target = "commentId", source = "comment.commentId")
    @Mapping(target = "taskId", source = "comment.taskComment.taskId")
    CommentResponse commentToCommentResponse(Comment comment);
}
