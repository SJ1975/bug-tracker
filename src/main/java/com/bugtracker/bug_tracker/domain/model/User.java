package com.bugtracker.bug_tracker.domain.model;

import com.bugtracker.bug_tracker.domain.enums.Role;

public class User {

    private final Long id;
    private final Role role;

    public User(Long id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}