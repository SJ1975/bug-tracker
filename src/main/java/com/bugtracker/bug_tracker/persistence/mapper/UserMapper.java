package com.bugtracker.bug_tracker.persistence.mapper;

import com.bugtracker.bug_tracker.domain.model.User;
import com.bugtracker.bug_tracker.persistence.entity.UserEntity;

public final class UserMapper {

    private UserMapper() {}

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getRole()
        );
    }
}