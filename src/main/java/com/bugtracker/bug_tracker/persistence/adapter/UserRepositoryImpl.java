package com.bugtracker.bug_tracker.persistence.adapter;

import com.bugtracker.bug_tracker.application.port.UserRepository;
import com.bugtracker.bug_tracker.domain.model.User;
import com.bugtracker.bug_tracker.persistence.mapper.UserMapper;
import com.bugtracker.bug_tracker.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User load(Long userId) {
        return userJpaRepository.findById(userId)
                .map(UserMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
