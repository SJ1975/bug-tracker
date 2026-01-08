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
                entity.getProject().getId()
        );
    }

    public static BugEntity toEntity(Bug domain, UserEntity reporter, ProjectEntity project) {
        BugEntity entity = new BugEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setStatus(domain.getStatus());
        entity.setPriority(domain.getPriority());
        entity.setReporter(reporter);
        entity.setProject(project);
        return entity;
    }
}