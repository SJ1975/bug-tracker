package com.bugtracker.bug_tracker.application.usecase;

import com.bugtracker.bug_tracker.application.port.BugRepository;
import com.bugtracker.bug_tracker.domain.enums.Role;
import com.bugtracker.bug_tracker.domain.model.Bug;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BugQueryService {

    private final BugRepository bugRepository;

    public BugQueryService(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    public List<Bug> findBugsForUser(Long userId, Role role) {

        return switch (role) {
            case ADMIN -> bugRepository.findAll();
            case REPORTER -> bugRepository.findByReporterId(userId);
            case DEVELOPER -> bugRepository.findByDeveloperId(userId);
            case TESTER -> bugRepository.findByTesterId(userId);
        };
    }

    public Bug findBugByIdForUser(Long bugId, Long userId, Role role) {

        Bug bug = bugRepository.load(bugId);

        return switch (role) {

            case ADMIN -> bug;

            case REPORTER -> {
                if (!bug.getReporterId().equals(userId)) {
                    throw new AccessDeniedException("Reporter can view only own bugs");
                }
                yield bug;
            }

            case DEVELOPER -> {
                if (bug.getDeveloperId() == null ||
                        !bug.getDeveloperId().equals(userId)) {
                    throw new AccessDeniedException("Developer can view only assigned bugs");
                }
                yield bug;
            }

            case TESTER -> {
                if (bug.getTesterId() == null ||
                        !bug.getTesterId().equals(userId)) {
                    throw new AccessDeniedException("Tester can view only assigned bugs");
                }
                yield bug;
            }
        };
    }
}
