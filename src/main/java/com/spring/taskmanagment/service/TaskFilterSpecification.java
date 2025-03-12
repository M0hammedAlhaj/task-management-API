package com.spring.taskmanagment.service;

import com.spring.taskmanagment.model.TaskPriority;
import com.spring.taskmanagment.model.TaskStatus;
import com.spring.taskmanagment.model.entity.Task;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TaskFilterSpecification implements Specification<Task> {

    private TaskPriority taskPriority;

    private TaskStatus taskStatus;

    private Long userID;

    public TaskFilterSpecification(TaskPriority taskPriority,
                                   TaskStatus taskStatus
            , Long userID) {
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
        this.userID = userID;
    }

    @Override
    public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (taskPriority != null) {
            predicates.add(criteriaBuilder.equal(root.get("priority"), taskPriority));
        }
        if (taskStatus != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), taskStatus));
        }

        Predicate userOwnerPredicate = criteriaBuilder.equal(root.get("userOwner").get("userId"), userID);
        Predicate userAssignPredicate = criteriaBuilder.equal(root.get("userAssign").get("userId"), userID);
        predicates.add(criteriaBuilder.or(userAssignPredicate, userOwnerPredicate));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
