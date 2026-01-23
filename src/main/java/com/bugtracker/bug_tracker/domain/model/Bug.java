package com.bugtracker.bug_tracker.domain.model;

import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.enums.Priority;
import com.bugtracker.bug_tracker.domain.enums.Role;
import com.bugtracker.bug_tracker.domain.rules.BugLifecycleRules;
import com.bugtracker.bug_tracker.domain.enums.BugStatus;

import java.time.LocalDateTime;

public class Bug {
    private Long id;
    private String title;
    private String description;
    private BugStatus status;
    private final Priority priority;
    private final Long projectId;
    private Long reporterId;
    private Long developerId;
    private Long testerId;
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

    public Long getDeveloperId() {
        return developerId;
    }

    public Long getTesterId() {
        return testerId;
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
            Long projectId,
            Long developerId,
            Long testerId,
            BugStatus status
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.reporterId = reporterId;
        this.projectId = projectId;
        this.developerId = developerId;
        this.testerId = testerId;
        this.status = status;
    }

    public Bug(
            String title,
            String description,
            Priority priority,
            Long reporterId,
            Long projectId
    ) {
        this.id = null;                 // not saved yet
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.reporterId = reporterId;
        this.projectId = projectId;

        this.developerId = null;
        this.testerId = null;
        this.status = BugStatus.OPEN;   // ALWAYS OPEN on creation
    }


    public Long getProjectId() {
        return projectId;
    }

//    public void changeStatus(BugStatus newStatus) {
//        BugLifecycleRules.validate(this.status, newStatus);
//        this.status = newStatus;
//    }

//    public void changeStatus(
//            BugStatus newStatus,
//            Role actorRole,
//            Long actorUserId
//    ) {
//        // 1️⃣ Block solved bugs
//        if (this.status == BugStatus.SOLVED) {
//            throw new IllegalStateException("Solved bugs cannot be changed");
//        }
//
//        // 2️⃣ Lifecycle validation
//        BugLifecycleRules.validate(this.status, newStatus);
//
//        // 3️⃣ Role-based rules
//        switch (actorRole) {
//
//            case ADMIN -> {
//                // ADMIN can do anything
//            }
//
//            case REPORTER -> {
//                if (!this.reporterId.equals(actorUserId)) {
//                    throw new IllegalStateException("Reporter can change only own bugs");
//                }
//                if (this.status != BugStatus.OPEN || newStatus != BugStatus.IN_PROGRESS) {
//                    throw new IllegalStateException("Reporter can only move OPEN → IN_PROGRESS");
//                }
//            }
//
//            case DEVELOPER, TESTER -> {
//                if (!actorUserId.equals(this.assigneeId)) {
//                    throw new IllegalStateException("Only assignee can resolve the bug");
//                }
//                if (this.status != BugStatus.IN_PROGRESS || newStatus != BugStatus.SOLVED) {
//                    throw new IllegalStateException("Only IN_PROGRESS → SOLVED allowed");
//                }
//            }
//        }
//
//        // 4️⃣ Apply change
//        this.status = newStatus;
//    }


    public void changeStatus(BugStatus newStatus, Role actorRole) {
        BugLifecycleRules.validate(this.status, newStatus, actorRole);
        this.status = newStatus;
    }


    public void assignDeveloper(Long developerId) {
        this.developerId = developerId;
    }

    public void assignTester(Long testerId) {
        this.testerId = testerId;
    }



//    public void assignTo(Long assigneeId) {
//        this.assigneeId = assigneeId;
//    }
    // getters only (immutability mindset)


}
