package com.bugtracker.bug_tracker.api.dto;

import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.enums.Priority;
import com.bugtracker.bug_tracker.domain.model.Bug;

public class BugResponse {

    private Long id;
    private String title;
    private String description;
    private BugStatus status;
    private Priority priority;

    public static BugResponse from(Bug bug) {
        BugResponse response = new BugResponse();
        response.id = bug.getId();
        response.title = bug.getTitle();
        response.description = bug.getDescription();
        response.status = bug.getStatus();
        response.priority = bug.getPriority();
        return response;
    }

    // getters & setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
