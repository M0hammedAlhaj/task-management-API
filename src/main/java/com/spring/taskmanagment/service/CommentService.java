package com.spring.taskmanagment.service;


import com.spring.taskmanagment.dao.CommentDao;
import com.spring.taskmanagment.dto.comment.CommentCreationRequest;
import com.spring.taskmanagment.dto.comment.CommentResponse;
import com.spring.taskmanagment.exception.ResourceNotFoundException;
import com.spring.taskmanagment.mapper.CommentMap;
import com.spring.taskmanagment.model.entity.Comment;
import com.spring.taskmanagment.model.entity.Task;
import com.spring.taskmanagment.model.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentDao commentDao;

    private final UserService userService;

    private final TaskService taskService;

    private final CommentMap commentMap;

    public CommentService(CommentDao commentDao, UserService userService, TaskService taskService, CommentMap commentMap) {

        this.commentDao = commentDao;
        this.userService = userService;
        this.taskService = taskService;
        this.commentMap = commentMap;
    }


    @CachePut(value = "comment", key = "#commentCreationRequest.taskId")
    @Transactional
    public CommentResponse saveComment(CommentCreationRequest commentCreationRequest, String userCommentEmail) {

        Task task = taskService.findTaskById(commentCreationRequest.getTaskId());

        User user = userService.findUserByEmail(userCommentEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        taskService.validateUserParticipated(task.getTaskId(), userCommentEmail);

        Comment comment = new Comment();

        comment.setContent(commentCreationRequest.getContent());
        comment.setUserAuthor(user);
        comment.setTaskComment(task);

        comment = commentDao.save(comment);

        return commentMap.commentToCommentResponse(comment);
    }

    @Caching(
            evict = {@CacheEvict(value = "comment", key = "#commentIdUpdate"),
                    @CacheEvict(value = "comments", key = "#commentCreationRequest.taskId")},

            put = {@CachePut(value = "comment", key = "#commentIdUpdate")}
    )
    @Transactional
    public CommentResponse updateComment(CommentCreationRequest commentCreationRequest,
                                         Long commentIdUpdate,
                                         String userCommentEmail) {

        User userRequest = userService.
                findUserByEmail(userCommentEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment comment = commentDao
                .findById(commentIdUpdate).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        validateOwnerComment(userRequest, comment);


        comment.setContent(commentCreationRequest.getContent());
        commentDao.save(comment);

        return commentMap.commentToCommentResponse(comment);
    }

    @Cacheable(value = "comments", key = "#taskId")
    public List<CommentResponse> findCommentsByTaskId(Long taskId) {

        return commentDao.findCommentsByTaskCommentTaskId(taskId)
                .stream()
                .map(commentMap::commentToCommentResponse)
                .toList();
    }

    @Caching(evict = {@CacheEvict(value = "comment", key = "#commentId"),
            @CacheEvict(value = "comments", key = "#result.taskId")})

    @Transactional
    public CommentResponse deleteCommentById(Long commentId, String userEmail) {
        Comment comment = commentDao.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        User user = userService
                .findUserByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateOwnerComment(user, comment);

        commentDao.delete(comment);
        return commentMap.commentToCommentResponse(comment);
    }

    @Cacheable(value = "comment", key = "#commentId")
    public CommentResponse findCommentById(Long commentId) {

        Comment comment = commentDao.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        return commentMap.commentToCommentResponse(comment);
    }

    public void validateOwnerComment(User ownerComment, Comment comment) {
        if (!comment.getUserAuthor().equals(ownerComment)) {
            throw new AuthorizationServiceException("User not have access to update comment");
        }
    }

}
