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
            Long currentUserId,
            Role currentUserRole
    ) {
        Bug bug = bugRepository.load(bugId);

        if (currentUserRole != Role.ADMIN &&
                !bug.getReporterId().equals(currentUserId)) {
            throw new AccessDeniedException("Not allowed to change this bug");
        }

        bug.changeStatus(newStatus);
        bugRepository.save(bug);
    }
}
