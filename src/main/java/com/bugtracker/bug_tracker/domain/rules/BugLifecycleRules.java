package com.bugtracker.bug_tracker.domain.rules;

import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.enums.Role;

public final class BugLifecycleRules {

    private BugLifecycleRules() {}

    public static void validate(BugStatus current, BugStatus next) {

        if (current == BugStatus.CLOSED) {
            throw new IllegalStateException("Closed bug cannot be changed");
        }

        if (current == BugStatus.OPEN && next == BugStatus.OPEN) {
            throw new IllegalStateException("Bug is already OPEN");
        }

        // add more state-only rules here
    }
}