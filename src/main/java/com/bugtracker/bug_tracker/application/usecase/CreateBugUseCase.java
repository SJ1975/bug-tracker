package com.bugtracker.bug_tracker.application.usecase;

import com.bugtracker.bug_tracker.application.dto.CreateBugCommand;
import com.bugtracker.bug_tracker.application.port.BugAuditRepository;
import com.bugtracker.bug_tracker.application.port.BugRepository;
import com.bugtracker.bug_tracker.application.port.ProjectRepository;
import com.bugtracker.bug_tracker.application.port.UserRepository;
import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.enums.Role;
import com.bugtracker.bug_tracker.domain.model.Bug;
import com.bugtracker.bug_tracker.domain.model.BugAuditLog;
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
    private final BugAuditRepository auditRepository;

    public CreateBugUseCase(
            BugRepository bugRepository,
            UserRepository userRepository,
            ProjectRepository projectRepository,
            BugAuditRepository auditRepository
    ) {
        this.bugRepository = bugRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.auditRepository = auditRepository;
    }

    @Transactional
    public Bug execute(
            CreateBugCommand command,
            Long actorId,
            Role actorRole) {

        if (actorRole != Role.REPORTER) {
            throw new AccessDeniedException("Only REPORTER can create bugs");
        }

        Bug bug = new Bug(
                command.title(),
                command.description(),
                command.priority(),
                command.reporterId(),
                command.projectId()
        );


//        return bugRepository.create(bug);
        Bug saved = bugRepository.create(bug);

        auditRepository.save(
                new BugAuditLog(
                        saved.getId(),
                        "BUG_CREATED",
                        null,
                        "OPEN",
                        actorId,
                        actorRole.name()
                )
        );

        return saved;
    }

}
