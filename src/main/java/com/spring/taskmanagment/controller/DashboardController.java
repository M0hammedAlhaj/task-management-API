package com.spring.taskmanagment.controller;

import com.spring.taskmanagment.model.TaskStatus;
import com.spring.taskmanagment.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/dashboards")
public class DashboardController {


    private final DashboardService dashboardService;


    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // Admin: Monthly user registrations
    @GetMapping("/stats/user-registrations")
    public ResponseEntity<Long> getMonthlyUserRegistrations() {
        return ResponseEntity.ok(dashboardService.countUsersCreatedMonthFromDay());
    }

    // User: Count tasks by status
    @GetMapping("/tasks/count-by-status")
    public ResponseEntity<Long> countTasksByStatus(@RequestParam TaskStatus taskStatus,
                                                   @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.
                ok(dashboardService.countTaskByStatus(taskStatus, userDetails.getUsername()));
    }

    // Owner: Incomplete task counts per assignee
    @GetMapping("/tasks/assignees/incomplete-counts")
    public ResponseEntity<HashMap<Long, Long>> getIncompleteTaskCountsPerAssignee(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(dashboardService.
                        findTaskCountsPerUserAssignByOwnerEmailAndStatusNotCompleted
                                (userDetails.getUsername())
                );
    }

    @GetMapping("/tasks/completion-rate")
    public BigDecimal getTaskCompletionRateByUserEmail(@RequestParam("email") String userEmail) {
        return dashboardService.taskCompletedRatByUserEmail(userEmail);
    }

}
