package com.spring.taskmanagment.dao;

import com.spring.taskmanagment.model.TaskStatus;
import com.spring.taskmanagment.model.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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

}
