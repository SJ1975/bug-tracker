package com.bugtracker.bug_tracker.api.controller;

import com.bugtracker.bug_tracker.api.dto.BugAuditResponse;
import com.bugtracker.bug_tracker.api.util.AuditMaskingUtil;
import com.bugtracker.bug_tracker.application.usecase.GetBugHistoryUseCase;
import com.bugtracker.bug_tracker.domain.enums.Role;
import com.bugtracker.bug_tracker.domain.model.BugAuditLog;
import com.bugtracker.bug_tracker.security.CustomUserDetails;
import com.bugtracker.bug_tracker.security.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bugs")
public class BugHistoryController {

    private final GetBugHistoryUseCase historyUseCase;

    public BugHistoryController(GetBugHistoryUseCase historyUseCase) {
        this.historyUseCase = historyUseCase;
    }

    @GetMapping("/{id}/history")
    public Page<BugAuditResponse> getHistory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        CustomUserDetails user = SecurityUtils.currentUser();

        Sort sorting = Sort.by(
                sort.split(",")[0]
        ).descending();

        Pageable pageable = PageRequest.of(page, size, sorting);

        return historyUseCase.execute(
                        id,
                        user.getUserId(),
                        user.getRole(),
                        pageable
                )
                .map(log -> toResponse(log, user.getRole()));
    }

    private BugAuditResponse toResponse(
            BugAuditLog log,
            Role viewerRole
    ) {
        BugAuditResponse r = new BugAuditResponse();

        r.setAction(log.getAction());

        r.setOldValue(
                AuditMaskingUtil.maskValue(
                        log.getOldValue(),
                        viewerRole
                )
        );

        r.setNewValue(
                AuditMaskingUtil.maskValue(
                        log.getNewValue(),
                        viewerRole
                )
        );

        r.setActorRole(
                AuditMaskingUtil.maskActorRole(
                        log.getActorRole(),
                        viewerRole
                )
        );

        r.setActorId(
                AuditMaskingUtil.maskActorId(
                        log.getActorId(),
                        viewerRole
                )
        );

        return r;

    }

}
