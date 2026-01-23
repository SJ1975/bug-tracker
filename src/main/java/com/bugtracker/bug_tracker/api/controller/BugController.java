package com.bugtracker.bug_tracker.api.controller;

import com.bugtracker.bug_tracker.api.dto.AssignBugRequest;
import com.bugtracker.bug_tracker.api.dto.BugResponse;
import com.bugtracker.bug_tracker.api.dto.ChangeBugStatusRequest;
import com.bugtracker.bug_tracker.api.dto.CreateBugRequest;
import com.bugtracker.bug_tracker.application.dto.CreateBugCommand;
import com.bugtracker.bug_tracker.application.usecase.AssignBugUseCase;
import com.bugtracker.bug_tracker.application.usecase.ChangeBugStatusUseCase;
import com.bugtracker.bug_tracker.application.usecase.CreateBugUseCase;
import com.bugtracker.bug_tracker.domain.model.Bug;
import com.bugtracker.bug_tracker.security.CustomUserDetails;
import com.bugtracker.bug_tracker.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bugs")
@PreAuthorize("isAuthenticated()") // 🔐 All endpoints require JWT
public class BugController {

    private final CreateBugUseCase createBugUseCase;
    private final ChangeBugStatusUseCase changeBugStatusUseCase;
    private final AssignBugUseCase assignBugUseCase;

    public BugController(
            CreateBugUseCase createBugUseCase,
            ChangeBugStatusUseCase changeBugStatusUseCase,
            AssignBugUseCase assignBugUseCase
    ) {
        this.createBugUseCase = createBugUseCase;
        this.changeBugStatusUseCase = changeBugStatusUseCase;
        this.assignBugUseCase = assignBugUseCase;
    }

    // ================= CREATE BUG =================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BugResponse createBug(
            @Valid @RequestBody CreateBugRequest request
    ) {
        Long reporterId = SecurityUtils.currentUserId();

        Bug bug = createBugUseCase.execute(
                new CreateBugCommand(
                        request.getTitle(),
                        request.getDescription(),
                        request.getPriority(),
                        reporterId,
                        request.getProjectId()
                )
        );

        return BugResponse.from(bug);
    }

    // ================= CHANGE STATUS =================

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChangeBugStatusRequest request
    ) {
        CustomUserDetails user = SecurityUtils.currentUser();

        changeBugStatusUseCase.execute(
                id,
                request.getStatus(),
                user.getUserId(),
                user.getRole()
        );
    }

    @PatchMapping("/{id}/assign")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignBug(
            @PathVariable Long id,
            @Valid @RequestBody AssignBugRequest request
    ) {
        CustomUserDetails user = SecurityUtils.currentUser();

        assignBugUseCase.execute(
                id,
                request.getAssigneeId(),
                user.getUserId(),
                user.getRole()
        );
    }

}
