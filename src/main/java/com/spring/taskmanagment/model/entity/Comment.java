package com.spring.taskmanagment.model.entity;

import com.spring.taskmanagment.model.TimeLogType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @NotBlank(message = "Content must not be blank")
    @Size(max = 1000, message = "Content must not exceed 1000 characters")
    private String content;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "time_log_comment",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "time_log_id")
    )
    private Set<TimeLog> timeLogs = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        TimeLog timeLog = new TimeLog();
        timeLog.setType(TimeLogType.CREATE);
        timeLog.setDate(LocalDateTime.now());
        timeLogs.add(timeLog);
    }

    @PreUpdate
    protected void onUpdate() {
        TimeLog timeLog = new TimeLog();
        timeLog.setType(TimeLogType.UPDATE);
        timeLog.setDate(LocalDateTime.now());
        timeLogs.add(timeLog);
    }


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userAuthor;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task taskComment;


    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<TimeLog> getTimeLogs() {
        return timeLogs;
    }

    public void setTimeLogs(Set<TimeLog> timeLogs) {
        this.timeLogs = timeLogs;
    }

    public User getUserAuthor() {
        return userAuthor;
    }

    public void setUserAuthor(User userAuthor) {
        this.userAuthor = userAuthor;
    }

    public Task getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(Task taskComment) {
        this.taskComment = taskComment;
    }
}
