package com.bugtracker.bug_tracker.application.port;


import com.bugtracker.bug_tracker.domain.model.Bug;

import java.util.List;

public interface BugRepository {

    Bug load(Long bugId);

    Bug create(Bug bug);

    void save(Bug bug);

    List<Bug> findAll();

    List<Bug> findByReporterId(Long reporterId);

    List<Bug> findByDeveloperId(Long developerId);

    List<Bug> findByTesterId(Long testerId);
}

