package com.bugtracker.bug_tracker.api.dto;

import jakarta.validation.constraints.NotNull;

public class AssignDeveloperRequest {

    @NotNull
    private Long developerId;

    public Long getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Long developerId) {
        this.developerId = developerId;
    }
}
