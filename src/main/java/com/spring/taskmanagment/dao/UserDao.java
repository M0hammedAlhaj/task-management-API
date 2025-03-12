package com.spring.taskmanagment.dao;

import com.spring.taskmanagment.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);


    @Query("SELECT COUNT(u) FROM User u " +
            "JOIN u.timeLogs t " +
            "WHERE t.date BETWEEN :startDate AND :endDate and t.type='CREATE'")
    Long findUsersCreationCount(@Param("startDate") LocalDateTime startDate,
                                @Param("endDate") LocalDateTime endDate);

}
