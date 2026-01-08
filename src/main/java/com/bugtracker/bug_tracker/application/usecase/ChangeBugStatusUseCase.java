package com.bugtracker.bug_tracker.application.usecase;

import com.bugtracker.bug_tracker.application.port.BugRepository;
import com.bugtracker.bug_tracker.application.port.UserRepository;
import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.model.Bug;
import com.bugtracker.bug_tracker.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeBugStatusUseCase {

    private final BugRepository bugRepository;
    private final UserRepository userRepository;

    public ChangeBugStatusUseCase(
            BugRepository bugRepository,
            UserRepository userRepository
    ) {
        this.bugRepository = bugRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(Long bugId, BugStatus newStatus, Long actorId) {

        Bug bug = bugRepository.load(bugId);
        User actor = userRepository.load(actorId);

        bug.changeStatus(newStatus, actor.getRole());

        bugRepository.save(bug);
    }
}
