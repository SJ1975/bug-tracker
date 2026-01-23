package com.bugtracker.bug_tracker.application.usecase;

import com.bugtracker.bug_tracker.application.port.BugRepository;
import com.bugtracker.bug_tracker.application.port.UserRepository;
import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.enums.Role;
import com.bugtracker.bug_tracker.domain.model.Bug;
import com.bugtracker.bug_tracker.domain.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeBugStatusUseCase {

    private final BugRepository bugRepository;

    public ChangeBugStatusUseCase(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    @Transactional
    public void execute(
            Long bugId,
            BugStatus newStatus,
            Long actorId,
            Role actorRole
    ) {
        Bug bug = bugRepository.load(bugId);

        // 1️⃣ Ownership + RBAC
        validateOwnership(bug, actorId, actorRole);

        // 2️⃣ Lifecycle + role rules (domain)
        bug.changeStatus(newStatus, actorRole);

        // 3️⃣ Persist
        bugRepository.save(bug);
    }

    private void validateOwnership(Bug bug, Long actorId, Role actorRole) {

        // ADMIN can do anything
        if (actorRole == Role.ADMIN) {
            return;
        }

        switch (actorRole) {

            case DEVELOPER -> {
                if (bug.getDeveloperId() == null) {
                    throw new AccessDeniedException("No developer assigned to this bug");
                }
                if (!actorId.equals(bug.getDeveloperId())) {
                    throw new AccessDeniedException("Only assigned developer can do this");
                }
            }

            case TESTER -> {
                if (bug.getTesterId() == null) {
                    throw new AccessDeniedException("No tester assigned to this bug");
                }
                if (!actorId.equals(bug.getTesterId())) {
                    throw new AccessDeniedException("Only assigned tester can do this");
                }
            }

            case REPORTER -> {
                throw new AccessDeniedException(
                        "Reporter is not allowed to change bug status"
                );
            }
        }
    }
}
