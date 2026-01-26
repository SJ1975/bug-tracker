package com.bugtracker.bug_tracker.api.controller;

import com.bugtracker.bug_tracker.api.dto.*;
import com.bugtracker.bug_tracker.application.dto.CreateBugCommand;
import com.bugtracker.bug_tracker.application.usecase.*;
import com.bugtracker.bug_tracker.domain.model.Bug;
import com.bugtracker.bug_tracker.security.CustomUserDetails;
import com.bugtracker.bug_tracker.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bugs")
@PreAuthorize("isAuthenticated()") // 🔐 All endpoints require JWT
public class BugController {

    private final CreateBugUseCase createBugUseCase;
    private final ChangeBugStatusUseCase changeBugStatusUseCase;
    private final AssignDeveloperUseCase assignDeveloperUseCase;
    private final AssignTesterUseCase assignTesterUseCase;
    private final BugQueryService bugQueryService;

    public BugController(
            CreateBugUseCase createBugUseCase,
            ChangeBugStatusUseCase changeBugStatusUseCase,
            AssignDeveloperUseCase assignDeveloperUseCase,
            AssignTesterUseCase assignTesterUseCase,
            BugQueryService bugQueryService

    ) {
        this.createBugUseCase = createBugUseCase;
        this.changeBugStatusUseCase = changeBugStatusUseCase;
        this.assignDeveloperUseCase = assignDeveloperUseCase;
        this.assignTesterUseCase = assignTesterUseCase;
        this.bugQueryService = bugQueryService;
    }

    // ================= CREATE BUG =================

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BugResponse createBug(
            @Valid @RequestBody CreateBugRequest request
    ) {

        CustomUserDetails user = SecurityUtils.currentUser();
        Long reporterId = SecurityUtils.currentUserId();

        Bug bug = createBugUseCase.execute(
                new CreateBugCommand(
                        request.getTitle(),
                        request.getDescription(),
                        request.getPriority(),
                        reporterId,
                        request.getProjectId()
                ),
                user.getUserId(),
                user.getRole()
        );

        return BugResponse.from(bug);
    }

    private BugResponse toResponse(Bug bug) {
        BugResponse response = new BugResponse();
        response.setId(bug.getId());
        response.setTitle(bug.getTitle());
        response.setDescription(bug.getDescription());
        response.setStatus(bug.getStatus());
        response.setPriority(bug.getPriority());
        return response;
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
    @PatchMapping("/{id}/assign-developer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignDeveloper(
            @PathVariable Long id,
            @Valid @RequestBody AssignDeveloperRequest request
    ) {
        CustomUserDetails user = SecurityUtils.currentUser();

        assignDeveloperUseCase.execute(
                id,
                request.getDeveloperId(),
                user.getUserId(),
                user.getRole()
        );
    }

    @PatchMapping("/{id}/assign-tester")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignTester(
            @PathVariable Long id,
            @Valid @RequestBody AssignTesterRequest request
    ) {
        CustomUserDetails user = SecurityUtils.currentUser();

        assignTesterUseCase.execute(
                id,
                request.getTesterId(),
                user.getUserId(),
                user.getRole()
        );
    }


    @GetMapping
    public ResponseEntity<List<BugResponse>> getBugs() {

        CustomUserDetails user = SecurityUtils.currentUser();

        List<Bug> bugs = bugQueryService.findBugsForUser(
                user.getUserId(),
                user.getRole()
        );

        List<BugResponse> response = bugs.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BugResponse> getBugById(@PathVariable Long id) {

        CustomUserDetails user = SecurityUtils.currentUser();

        Bug bug = bugQueryService.findBugByIdForUser(
                id,
                user.getUserId(),
                user.getRole()
        );

        return ResponseEntity.ok(toResponse(bug));
    }


}
