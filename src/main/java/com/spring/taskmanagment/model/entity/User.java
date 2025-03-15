package com.spring.taskmanagment.model.entity;

import com.spring.taskmanagment.model.TimeLogType;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Builder
@NoArgsConstructor
@AllArgsConstructor
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


}
