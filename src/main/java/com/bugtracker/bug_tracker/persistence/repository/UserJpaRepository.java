package com.bugtracker.bug_tracker.persistence.repository;

import com.bugtracker.bug_tracker.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
