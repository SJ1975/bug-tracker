package com.bugtracker.bug_tracker.persistence.mapper;

import com.bugtracker.bug_tracker.domain.model.Bug;
import com.bugtracker.bug_tracker.persistence.entity.BugEntity;
import com.bugtracker.bug_tracker.persistence.entity.ProjectEntity;
import com.bugtracker.bug_tracker.persistence.entity.UserEntity;

public final class BugMapper {

    private BugMapper() {}

    public static Bug toDomain(BugEntity entity) {
        return new Bug(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getPriority(),
                entity.getReporter().getId(),
                entity.getProject().getId(),
                entity.getDeveloper() != null ? entity.getDeveloper().getId() : null,
                entity.getTester() != null ? entity.getTester().getId() : null,
                entity.getStatus()
        );
    }

    public static BugEntity toEntity(
            Bug bug,
            UserEntity reporter,
            ProjectEntity project,
            UserEntity developer,
            UserEntity tester
    ) {
        BugEntity entity = new BugEntity();
        entity.setId(bug.getId());
        entity.setTitle(bug.getTitle());
        entity.setDescription(bug.getDescription());
        entity.setPriority(bug.getPriority());
        entity.setStatus(bug.getStatus());
        entity.setReporter(reporter);
        entity.setProject(project);
        entity.setDeveloper(developer);
        entity.setTester(tester);
        return entity;
    }
}