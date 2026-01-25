package com.bugtracker.bug_tracker.application.port;

import com.bugtracker.bug_tracker.domain.model.BugAuditLog;

import java.util.List;

public interface BugAuditRepository {
    void save(BugAuditLog log);
    List<BugAuditLog> findByBugId(Long bugId);
}
