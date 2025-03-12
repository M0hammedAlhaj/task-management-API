package com.spring.taskmanagment.model.entity;

import com.spring.taskmanagment.model.TimeLogType;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "userAuthor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "userOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Task> taskOwned = new HashSet<>();

    @OneToMany(mappedBy = "userAssign")
    private Set<Task> taskAssigned = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "time_log_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "time_log_id"))
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

    @PreDestroy
    protected void onPreDestroy() {
        TimeLog timeLog = new TimeLog();
        timeLog.setType(TimeLogType.DELETE);
        timeLog.setDate(LocalDateTime.now());
        timeLogs.add(timeLog);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Task> getTaskOwned() {
        return taskOwned;
    }

    public void setTaskOwned(Set<Task> taskOwned) {
        this.taskOwned = taskOwned;
    }

    public Set<Task> getTaskAssigned() {
        return taskAssigned;
    }

    public void setTaskAssigned(Set<Task> taskAssigned) {
        this.taskAssigned = taskAssigned;
    }

    public Set<TimeLog> getTimeLogs() {
        return timeLogs;
    }

    public void setTimeLogs(Set<TimeLog> timeLogs) {
        this.timeLogs = timeLogs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }


}
