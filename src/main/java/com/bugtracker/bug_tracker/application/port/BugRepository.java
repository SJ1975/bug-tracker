package com.bugtracker.bug_tracker.application.port;


import com.bugtracker.bug_tracker.domain.model.Bug;

public interface BugRepository {
    Bug load(Long bugId);

    Bug save(Bug bug);
}

