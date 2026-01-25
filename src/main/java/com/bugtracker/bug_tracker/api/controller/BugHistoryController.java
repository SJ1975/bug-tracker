package com.bugtracker.bug_tracker.api.controller;

import com.bugtracker.bug_tracker.api.dto.BugAuditResponse;
import com.bugtracker.bug_tracker.application.usecase.GetBugHistoryUseCase;
import com.bugtracker.bug_tracker.domain.model.BugAuditLog;
import com.bugtracker.bug_tracker.security.CustomUserDetails;
import com.bugtracker.bug_tracker.security.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bugs")
public class BugHistoryController {

    private final GetBugHistoryUseCase historyUseCase;

    public BugHistoryController(GetBugHistoryUseCase historyUseCase) {
        this.historyUseCase = historyUseCase;
    }

    @GetMapping("/{id}/history")
    public List<BugAuditResponse> getHistory(@PathVariable Long id) {

        CustomUserDetails user = SecurityUtils.currentUser();

        return historyUseCase.execute(
                        id,
                        user.getUserId(),
                        user.getRole()
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private BugAuditResponse toResponse(BugAuditLog log) {
        BugAuditResponse r = new BugAuditResponse();
        r.setAction(log.getAction());
        r.setOldValue(log.getOldValue());
        r.setNewValue(log.getNewValue());
        r.setActorId(log.getActorId());
        r.setActorRole(log.getActorRole());
        return r;
    }
}

