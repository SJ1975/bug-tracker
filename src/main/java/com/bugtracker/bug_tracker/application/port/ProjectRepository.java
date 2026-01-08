package com.bugtracker.bug_tracker.application.port;


import com.bugtracker.bug_tracker.domain.model.Project;

public interface ProjectRepository {

    Project load(Long projectId);
}
