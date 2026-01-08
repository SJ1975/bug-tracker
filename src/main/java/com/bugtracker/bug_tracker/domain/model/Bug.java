package com.bugtracker.bug_tracker.domain.model;

import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.enums.Priority;
import com.bugtracker.bug_tracker.domain.enums.Role;
import com.bugtracker.bug_tracker.domain.rules.BugLifecycleRules;

import java.time.LocalDateTime;

public class Bug {
    private Long id;
    private String title;
    private String description;
    private BugStatus status;
    private Priority priority;
    private final Long projectId;
    private Long reporterId;
    private Long assigneeId;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BugStatus getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public Bug(
            Long id,
            String title,
            String description,
            Priority priority,
            Long reporterId,
            Long projectId
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.reporterId = reporterId;
        this.projectId = projectId;
        this.status = BugStatus.OPEN;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void changeStatus(BugStatus newStatus, Role actorRole) {
        BugLifecycleRules.validate(this.status, newStatus, actorRole);
        this.status = newStatus;
    }

    public void assignTo(Long assigneeId) {
        this.assigneeId = assigneeId;
    }
    // getters only (immutability mindset)

}
