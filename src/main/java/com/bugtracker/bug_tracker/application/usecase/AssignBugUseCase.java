package com.bugtracker.bug_tracker.application.usecase;

import com.bugtracker.bug_tracker.application.dto.AssignBugCommand;
import com.bugtracker.bug_tracker.application.port.BugRepository;
import com.bugtracker.bug_tracker.application.port.UserRepository;
import com.bugtracker.bug_tracker.domain.model.Bug;
import com.bugtracker.bug_tracker.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignBugUseCase {

    private final BugRepository bugRepository;
    private final UserRepository userRepository;

    public AssignBugUseCase(
            BugRepository bugRepository,
            UserRepository userRepository
    ) {
        this.bugRepository = bugRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void execute(AssignBugCommand command) {

        Bug bug = bugRepository.load(command.bugId());
        User assignee = userRepository.load(command.assigneeId());

        // NOTE: assignment is simple state change
        // business rules can be added later if needed

        bug.assignTo(assignee.getId());

        bugRepository.save(bug);
    }
}
