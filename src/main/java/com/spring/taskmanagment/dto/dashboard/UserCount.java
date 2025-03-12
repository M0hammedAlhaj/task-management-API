package com.spring.taskmanagment.dto.dashboard;

import java.io.Serializable;

public class UserCount implements Serializable {

    private Long userCount;

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }
}
