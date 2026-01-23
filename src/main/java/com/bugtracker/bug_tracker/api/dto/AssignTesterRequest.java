package com.bugtracker.bug_tracker.api.dto;

import jakarta.validation.constraints.NotNull;

public class AssignTesterRequest {

    @NotNull
    private Long testerId;

    public Long getTesterId() {
        return testerId;
    }

    public void setTesterId(Long testerId) {
        this.testerId = testerId;
    }
}