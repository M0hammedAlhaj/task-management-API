package com.spring.taskmanagment.dao;

import com.spring.taskmanagment.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {


    List<Comment> findCommentsByTaskCommentTaskId(Long taskId);
}
