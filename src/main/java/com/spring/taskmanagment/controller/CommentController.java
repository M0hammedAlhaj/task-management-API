package com.spring.taskmanagment.controller;

import com.spring.taskmanagment.dto.comment.CommentCreationRequest;
import com.spring.taskmanagment.dto.comment.CommentResponse;
import com.spring.taskmanagment.dto.notification.NotificationCommentResponse;
import com.spring.taskmanagment.service.CommentService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;


    private final ApplicationEventPublisher eventPublisher;


    public CommentController(CommentService commentService,
                             ApplicationEventPublisher eventPublisher) {

        this.commentService = commentService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentCreationRequest commentCreationRequest
            , @AuthenticationPrincipal UserDetails userAuthorComment) {


        String userAuthorEmail = userAuthorComment.getUsername();

        CommentResponse commentResponse = commentService.saveComment(commentCreationRequest, userAuthorEmail);


        eventPublisher.publishEvent(new NotificationCommentResponse(commentCreationRequest.getTaskId(), userAuthorEmail));

        return ResponseEntity.ok(commentResponse);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId,
                                                         @RequestBody CommentCreationRequest commentCreationRequest,
                                                         @AuthenticationPrincipal UserDetails userComment) {
        String userCommentEmail = userComment.getUsername();
        return ResponseEntity.ok(commentService.updateComment(commentCreationRequest, commentId, userCommentEmail));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<CommentResponse>> getComment(@PathVariable Long taskId) {

        return ResponseEntity.ok(commentService.findCommentsByTaskId(taskId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable Long commentId,
                                                         @AuthenticationPrincipal UserDetails userComment) {

        return ResponseEntity.ok(commentService.deleteCommentById(commentId, userComment.getUsername()));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.findCommentById(commentId));
    }
}
