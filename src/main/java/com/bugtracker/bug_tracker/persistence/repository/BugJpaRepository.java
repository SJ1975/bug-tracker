package com.bugtracker.bug_tracker.persistence.repository;

import com.bugtracker.bug_tracker.persistence.entity.BugEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BugJpaRepository extends JpaRepository<BugEntity, Long> {

    List<BugEntity> findByReporterId(Long reporterId);

    List<BugEntity> findByDeveloperId(Long developerId);

    List<BugEntity> findByTesterId(Long testerId);
}