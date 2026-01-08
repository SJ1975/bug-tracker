package com.bugtracker.bug_tracker.application.dto;

public record AssignBugCommand(
        Long bugId,
        Long assigneeId
) {}

