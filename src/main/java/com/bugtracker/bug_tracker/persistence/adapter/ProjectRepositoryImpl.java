package com.bugtracker.bug_tracker.persistence.adapter;

import com.bugtracker.bug_tracker.application.port.ProjectRepository;
import com.bugtracker.bug_tracker.domain.model.Project;
import com.bugtracker.bug_tracker.persistence.mapper.ProjectMapper;
import com.bugtracker.bug_tracker.persistence.repository.ProjectJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final ProjectJpaRepository projectJpaRepository;

    public ProjectRepositoryImpl(ProjectJpaRepository projectJpaRepository) {
        this.projectJpaRepository = projectJpaRepository;
    }

    @Override
    public Project load(Long projectId) {
        return projectJpaRepository.findById(projectId)
                .map(ProjectMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}
