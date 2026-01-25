package com.bugtracker.bug_tracker.domain.model;

import java.time.LocalDateTime;

public class BugAuditLog {

    private Long id;
    private Long bugId;
    private String action;
    private String oldValue;
    private String newValue;
    private Long actorId;
    private String actorRole;
    private LocalDateTime createdAt;

    public BugAuditLog(
            Long bugId,
            String action,
            String oldValue,
            String newValue,
            Long actorId,
            String actorRole
    ) {
        this.bugId = bugId;
        this.action = action;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.actorId = actorId;
        this.actorRole = actorRole;
    }

    // getters only

    public Long getId() {
        return id;
    }

    public Long getBugId() {
        return bugId;
    }

    public String getAction() {
        return action;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public Long getActorId() {
        return actorId;
    }

    public String getActorRole() {
        return actorRole;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
