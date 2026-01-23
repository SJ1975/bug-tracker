package com.bugtracker.bug_tracker.api.dto;

import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import jakarta.validation.constraints.NotNull;

public class ChangeBugStatusRequest {

    @NotNull
    private BugStatus status;

    @NotNull
//    private Long actorId;

    // getters & setters

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

//    public Long getActorId() {
//        return actorId;
//    }
//
//    public void setActorId(Long actorId) {
//        this.actorId = actorId;
//    }
}
