package com.bugtracker.bug_tracker.persistence.mapper;

import com.bugtracker.bug_tracker.domain.model.Project;
import com.bugtracker.bug_tracker.persistence.entity.ProjectEntity;

public final class ProjectMapper {

    private ProjectMapper() {}

    public static Project toDomain(ProjectEntity entity) {
        return new Project(
                entity.getId(),
                entity.getName()
        );
    }
}