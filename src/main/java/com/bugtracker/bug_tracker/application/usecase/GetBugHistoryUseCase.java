package com.bugtracker.bug_tracker.application.usecase;

import com.bugtracker.bug_tracker.application.port.BugAuditRepository;
import com.bugtracker.bug_tracker.application.port.BugRepository;
import com.bugtracker.bug_tracker.domain.enums.Role;
import com.bugtracker.bug_tracker.domain.model.Bug;
import com.bugtracker.bug_tracker.domain.model.BugAuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBugHistoryUseCase {

    private final BugRepository bugRepository;
    private final BugAuditRepository auditRepository;

    public GetBugHistoryUseCase(
            BugRepository bugRepository,
            BugAuditRepository auditRepository
    ) {
        this.bugRepository = bugRepository;
        this.auditRepository = auditRepository;
    }

    public Page<BugAuditLog> execute(
            Long bugId,
            Long actorId,
            Role actorRole,
            Pageable pageable
    ) {

        Bug bug = bugRepository.load(bugId);

        // 🔐 RBAC VISIBILITY RULES
        switch (actorRole) {
            case ADMIN -> { /* full access */ }

            case REPORTER -> {
                if (!bug.getReporterId().equals(actorId)) {
                    throw new AccessDeniedException("Not allowed");
                }
            }

            case DEVELOPER -> {
                if (!actorId.equals(bug.getDeveloperId())) {
                    throw new AccessDeniedException("Not allowed");
                }
            }

            case TESTER -> {
                if (!actorId.equals(bug.getTesterId())) {
                    throw new AccessDeniedException("Not allowed");
                }
            }
        }

        return auditRepository.findByBugId(bugId, pageable);
    }
}

