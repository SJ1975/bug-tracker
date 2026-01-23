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
public class AssignTesterUseCase {

    private final BugRepository bugRepository;
    private final UserRepository userRepository;

    public AssignTesterUseCase(
            BugRepository bugRepository,
            UserRepository userRepository
    ) {
        this.bugRepository = bugRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(
            Long bugId,
            Long testerId,
            Long actorId,
            Role actorRole
    ) {
        // 1️⃣ RBAC
        if (actorRole != Role.ADMIN) {
            throw new AccessDeniedException("Only ADMIN can assign tester");
        }

        // 2️⃣ Load aggregate
        Bug bug = bugRepository.load(bugId);

        // 3️⃣ Lifecycle guard
        if (bug.getStatus() != BugStatus.READY_FOR_TEST) {
            throw new IllegalStateException(
                    "Tester can be assigned only when bug is READY_FOR_TEST"
            );
        }

        // 4️⃣ Load tester
        User tester = userRepository.load(testerId);
        if (tester.getRole() != Role.TESTER) {
            throw new IllegalStateException("User is not a TESTER");
        }

        // 5️⃣ Assign tester
        bug.assignTester(testerId);

        // 6️⃣ Persist
        bugRepository.save(bug);
    }
}
