package com.bugtracker.bug_tracker.domain.model;

public class Project {

    private final Long id;
    private final String name;

    public Project(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}