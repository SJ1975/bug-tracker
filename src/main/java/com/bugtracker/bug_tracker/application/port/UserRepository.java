package com.bugtracker.bug_tracker.application.port;

import com.bugtracker.bug_tracker.domain.model.User;

public interface UserRepository {

        User load(Long userId);
    }

