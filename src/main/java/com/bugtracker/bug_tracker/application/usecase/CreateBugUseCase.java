package com.bugtracker.bug_tracker.application.usecase;

import com.bugtracker.bug_tracker.application.dto.CreateBugCommand;
import com.bugtracker.bug_tracker.application.port.BugRepository;
import com.bugtracker.bug_tracker.application.port.ProjectRepository;
import com.bugtracker.bug_tracker.application.port.UserRepository;
import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.enums.Role;
import com.bugtracker.bug_tracker.domain.model.Bug;
import com.bugtracker.bug_tracker.domain.model.Project;
import com.bugtracker.bug_tracker.domain.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateBugUseCase {

    private final BugRepository bugRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public CreateBugUseCase(
            BugRepository bugRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository
    ) {
        this.bugRepository = bugRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Bug execute(CreateBugCommand command) {

        if (command.actorRole() != Role.REPORTER) {
            throw new AccessDeniedException("Only REPORTER can create bugs");
        }

        Bug bug = new Bug(
                command.title(),
                command.description(),
                command.priority(),
                command.reporterId(),
                command.projectId()
        );


        return bugRepository.create(bug);
    }

}
