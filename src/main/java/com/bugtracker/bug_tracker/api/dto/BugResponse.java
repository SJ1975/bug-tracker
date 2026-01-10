package com.bugtracker.bug_tracker.api.dto;

import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.enums.Priority;

public class BugResponse {

    private Long id;
    private String title;
    private String description;
    private BugStatus status;
    private Priority priority;
//    private Long reporterId;

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

//    public Long getReporterId() {
//        return reporterId;
//    }
//
//    public void setReporterId(Long reporterId) {
//        this.reporterId = reporterId;
//    }
}
