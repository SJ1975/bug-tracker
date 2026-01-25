package com.bugtracker.bug_tracker.persistence.repository;

import com.bugtracker.bug_tracker.persistence.entity.BugAuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BugAuditJpaRepository
        extends JpaRepository<BugAuditLogEntity, Long> {

    // For history API later
    List<BugAuditLogEntity> findByBugIdOrderByCreatedAtAsc(Long bugId);
}