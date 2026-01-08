package com.bugtracker.bug_tracker.persistence.repository;

import com.bugtracker.bug_tracker.persistence.entity.BugEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugJpaRepository extends JpaRepository<BugEntity, Long> {

}
