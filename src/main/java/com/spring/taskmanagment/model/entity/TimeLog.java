package com.spring.taskmanagment.model.entity;

import com.spring.taskmanagment.model.TimeLogType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "time_log")

public class TimeLog implements Serializable {

    @Id
    @Column(name = "time_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeLogId;

    @Enumerated(EnumType.STRING)
    private TimeLogType type;

    private LocalDateTime date;


}
