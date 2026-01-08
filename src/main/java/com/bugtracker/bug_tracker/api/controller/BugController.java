package com.bugtracker.bug_tracker.api.controller;

import com.bugtracker.bug_tracker.api.dto.BugResponse;
import com.bugtracker.bug_tracker.api.dto.ChangeBugStatusRequest;
import com.bugtracker.bug_tracker.api.dto.CreateBugRequest;
import com.bugtracker.bug_tracker.application.dto.CreateBugCommand;
import com.bugtracker.bug_tracker.application.usecase.ChangeBugStatusUseCase;
import com.bugtracker.bug_tracker.application.usecase.CreateBugUseCase;
import com.bugtracker.bug_tracker.domain.model.Bug;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

    private final CreateBugUseCase createBugUseCase;
    private final ChangeBugStatusUseCase changeBugStatusUseCase;

    public BugController(
            CreateBugUseCase createBugUseCase,
            ChangeBugStatusUseCase changeBugStatusUseCase
    ) {
        this.createBugUseCase = createBugUseCase;
        this.changeBugStatusUseCase = changeBugStatusUseCase;
    }

    @PostMapping
    public ResponseEntity<BugResponse> createBug(
            @Valid @RequestBody CreateBugRequest request
    ) {
        Bug bug = createBugUseCase.execute(
                new CreateBugCommand(
                        request.getTitle(),
                        request.getDescription(),
                        request.getPriority(),
                        request.getReporterId(),
                        request.getProjectId()
                )
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(bug));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChangeBugStatusRequest request
    ) {
        changeBugStatusUseCase.execute(
                id,
                request.getStatus(),
                request.getActorId()
        );

        return ResponseEntity.noContent().build();
    }

    // ===== Mapping =====

    private BugResponse toResponse(Bug bug) {
        BugResponse response = new BugResponse();
        response.setId(bug.getId());
        response.setTitle(bug.getTitle());
        response.setDescription(bug.getDescription());
        response.setStatus(bug.getStatus());
        response.setPriority(bug.getPriority());
        response.setReporterId(bug.getReporterId());
        return response;
    }
}
