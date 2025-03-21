package com.spring.taskmanagment.dao;

import com.spring.taskmanagment.dto.task.TaskResponse;
import com.spring.taskmanagment.model.TaskStatus;
import com.spring.taskmanagment.model.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskDao extends JpaRepository<Task, Long> {


    Page<Task> findAll(Specification<Task> spec, Pageable pageable);


    @Query("SELECT COUNT(t.taskId) FROM Task t " +
            "JOIN t.userOwner uo " +
            "JOIN t.userAssign ua " +
            "WHERE (uo.email = :userEmail OR ua.email = :userEmail) " +
            "AND t.status <> :taskStatus")
    Optional<Long> countTaskByUserEmailAndTaskStatus(@Param("userEmail") String userEmail,
                                                     @Param("taskStatus") TaskStatus taskStatus);

    @Query("SELECT t.userAssign.userId ,COUNT(t.userAssign.userId) FROM Task t " +
            "JOIN t.userOwner uo " +
            "WHERE uo.email=:userEmail " +
            "AND t.status <> :taskStatus " +
            "GROUP BY t.userAssign")
    Optional<HashMap<Long, Long>> findTaskCountsPerUserAssignByOwnerEmailAndStatusNot(
            @Param("userEmail") String userEmail,
            @Param("taskStatus") TaskStatus taskStatus
    );

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN " +
            "COUNT(CASE WHEN t.status = :taskStatus THEN 1 END) * 1.0 / COUNT(t) " +
            "ELSE 0 END " +
            "FROM Task t " +
            "JOIN t.userAssign us " +
            "WHERE us.email = :userEmail")
    BigDecimal calculateTaskRatByUserEmailAndTaskStatus(@Param("userEmail") String userEmail,
                                                    @Param("taskStatus") TaskStatus taskStatus);

    @Query("SELECT t " +
            "FROM Task t " +
            "JOIN t.userAssign us " +
            "JOIN t.userOwner uo " +
            "WHERE t.taskName LIKE  %:taskName% " +
            "AND us.email=:userEmail OR uo.email=:userEmail"
    )
    List<Task> findTasksByUserEmailAndLikeTaskName(String taskName, String userEmail);
}
