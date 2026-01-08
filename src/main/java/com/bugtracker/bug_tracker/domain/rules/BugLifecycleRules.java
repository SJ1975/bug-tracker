package com.bugtracker.bug_tracker.domain.rules;

import com.bugtracker.bug_tracker.domain.enums.BugStatus;
import com.bugtracker.bug_tracker.domain.enums.Role;

public final class BugLifecycleRules {

    private BugLifecycleRules() {}

    public static void validate(
            BugStatus current,
            BugStatus next,
            Role role
    ) {
        if (current == BugStatus.CLOSED && next != BugStatus.REOPENED) {
            throw new IllegalStateException(
                    "Closed bugs can only be reopened"
            );
        }

        if (next == BugStatus.RESOLVED && role != Role.DEVELOPER) {
            throw new IllegalStateException(
                    "Only developers can resolve bugs"
            );
        }
    }
}