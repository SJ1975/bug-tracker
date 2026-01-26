package com.bugtracker.bug_tracker.application.port;

import com.bugtracker.bug_tracker.domain.model.BugAuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BugAuditRepository {
//    List<BugAuditLog> findByBugId(Long bugId);

    Page<BugAuditLog> findByBugId(
        Long bugId,
        Pageable pageable
    );

    void save(BugAuditLog log);
}
