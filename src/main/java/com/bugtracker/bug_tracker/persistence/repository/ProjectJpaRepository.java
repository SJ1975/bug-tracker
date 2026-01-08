package com.bugtracker.bug_tracker.persistence.repository;

import com.bugtracker.bug_tracker.persistence.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, Long> {
}