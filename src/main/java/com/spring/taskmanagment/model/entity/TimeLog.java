package com.spring.taskmanagment.model.entity;

import com.spring.taskmanagment.model.TimeLogType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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


    public Long getTimeLogId() {
        return timeLogId;
    }

    public void setTimeLogId(Long timeLogId) {
        this.timeLogId = timeLogId;
    }

    public TimeLogType getType() {
        return type;
    }

    public void setType(TimeLogType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
