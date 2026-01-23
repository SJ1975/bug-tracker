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
public class AssignDeveloperUseCase {

    private final BugRepository bugRepository;
    private final UserRepository userRepository;

    public AssignDeveloperUseCase(
            BugRepository bugRepository,
            UserRepository userRepository
    ) {
        this.bugRepository = bugRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(
            Long bugId,
            Long developerId,
            Long actorId,
            Role actorRole
    ) {
        // 1️⃣ RBAC
        if (actorRole != Role.ADMIN) {
            throw new AccessDeniedException("Only ADMIN can assign developer");
        }

        // 2️⃣ Load aggregate
        Bug bug = bugRepository.load(bugId);
        User developer = userRepository.load(developerId);

        // 3️⃣ Validate developer role
        if (developer.getRole() != Role.DEVELOPER) {
            throw new IllegalStateException("User is not a DEVELOPER");
        }

        // 4️⃣ Lifecycle guard
        if (bug.getStatus() == BugStatus.SOLVED) {
            throw new IllegalStateException("Cannot assign developer to solved bug");
        }

        // 5️⃣ If bug was REOPENED, admin explicitly restarts work
        if (bug.getStatus() == BugStatus.REOPEN) {
            bug.changeStatus(BugStatus.IN_PROGRESS, Role.ADMIN);
        }

        // 6️⃣ Assign developer
        bug.assignDeveloper(developerId);

        // 7️⃣ Persist
        bugRepository.save(bug);
    }
}
