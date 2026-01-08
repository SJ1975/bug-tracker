package com.bugtracker.bug_tracker.application.dto;

import com.bugtracker.bug_tracker.domain.enums.Priority;

public record CreateBugCommand(
        String title,
        String description,
        Priority priority,
        Long reporterId,
        Long projectId
) {}
